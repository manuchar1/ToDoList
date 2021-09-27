package com.example.todolist.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.app.snackBar
import com.example.todolist.databinding.DashboardFragmentBinding
import com.example.todolist.utils.Constants.CHECK_SCROLLING_UP
import com.example.todolist.utils.EventObserver
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


open class NotesFragment : Fragment() {


    private val viewModel: NotesViewModel by viewModels()
    private lateinit var binding: DashboardFragmentBinding
    private lateinit var noteApter: NoteAdapter


    private val uid = FirebaseAuth.getInstance().uid


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.init(requireContext())
        binding = DashboardFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteApter = NoteAdapter()

        init()


        binding.btnAddNotes.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_createNoteFragment)


        }
        hideFloatingButton()
        setupRecyclerView()
        subscribeToObservers()

        lifecycleScope.launch {
            noteApter.loadStateFlow.collectLatest {
                if (it.refresh is LoadState.Loading ||
                    it.append is LoadState.Loading
                ) {

                    binding.recyclerview.showShimmer()
                } else {
                    binding.recyclerview.hideShimmer()
                }
            }
        }


        noteApter.setOnNoteClickListener {

            val bundle = Bundle().apply {
                putSerializable("details", it)
            }
            findNavController().navigate(
                R.id.action_notesFragment_to_detailsFragment,
                bundle
            )
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun subscribeToObservers() {

        lifecycleScope.launch {
            if (uid != null) {
                viewModel.getPagingFlow(uid).collect {
                    noteApter.submitData(it)
                }
            }
        }

        viewModel.deleteNoteStatus.observe(viewLifecycleOwner, EventObserver(
            onError = { snackBar(it) },
            onLoading = {},
            onSuccess = {
                lifecycleScope.launch {
                    if (uid != null) {
                        viewModel.getPagingFlow(uid).collect {
                            noteApter.submitData(it)
                        }
                    }
                }
            }
        ))
    }


    private fun init() {

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val posotion = viewHolder.absoluteAdapterPosition

                val cart = noteApter.peek(posotion)
                if (cart != null) {
                    viewModel.deleteNote(cart)
                }

                view?.let {
                    snackBar("Deleted")

                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerview)
        }
    }

    private fun setupRecyclerView() = binding.recyclerview.apply {
        adapter = noteApter
        layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun hideFloatingButton() {


        CHECK_SCROLLING_UP
        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    if (CHECK_SCROLLING_UP) {
                        binding.btnAddNotes.startAnimation(
                            AnimationUtils.loadAnimation(
                                context,
                                R.anim.trans_upwards
                            )
                        )
                        CHECK_SCROLLING_UP = false
                    }
                } else {
                    if (!CHECK_SCROLLING_UP) {
                        binding.btnAddNotes
                            .startAnimation(
                                AnimationUtils
                                    .loadAnimation(context, R.anim.trans_downwards)
                            )
                        CHECK_SCROLLING_UP = true
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)


            }
        })

    }


}