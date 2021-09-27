package com.example.todolist.ui.dashboard

import android.annotation.SuppressLint
import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.app.snackBar
import com.example.todolist.data.Note
import com.example.todolist.databinding.DashboardFragmentBinding
import com.example.todolist.utils.BottomSheet
import com.example.todolist.utils.EventObserver
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


open class NotesFragment : Fragment() {


    private val viewModel: NotesViewModel by viewModels()
    private lateinit var binding: DashboardFragmentBinding
    private lateinit var noteApter: NoteAdapter

    private var curLikedIndex: Int? = null

    /*protected open val uid: String
        get() = FirebaseAuth.getInstance().uid!!*/

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

      //  init()




        binding.btnAddNotes.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_createNoteFragment)


        }


       /* var check_ScrollingUp = false
        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    // Scrolling up
                    if (check_ScrollingUp) {
                        binding.myView.startAnimation(
                            AnimationUtils.loadAnimation(
                                context,
                                R.anim.trans_upwards
                            )
                        )
                        check_ScrollingUp = false
                    }
                } else {
                    // User scrolls down
                    if (!check_ScrollingUp) {
                        binding.myView
                            .startAnimation(
                                AnimationUtils
                                    .loadAnimation(context, R.anim.trans_downwards)
                            )
                        check_ScrollingUp = true
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)


            }
        })*/


        setupRecyclerView()
        subscribeToObservers()
        //   subscribeToObservers2()

        lifecycleScope.launch {
            noteApter.loadStateFlow.collectLatest {
                binding.progressBar.isVisible = it.refresh is LoadState.Loading ||
                        it.append is LoadState.Loading
            }
        }

        noteApter.setOnLikeClickListener { note, i ->
            curLikedIndex = i
            note.isLiked = !note.isLiked
            viewModel.toggleCheckIconForNote(note)
        }



        noteApter.setOnNoteLongClickListener { note ->


            /*BottomSheet().apply {
                binding?.btnMarkAsDone?.setOnClickListener {
                    activity?.recreate()
                }
            }.show(childFragmentManager, null)*/

            // dismiss()
            // activity?.recreate()
            val bottomSheet = BottomSheet()
            bottomSheet.binding?.btnMarkAsDone?.setOnClickListener {
                snackBar("hdegdvetdvtevd")
                //  activity?.recreate()
            }

            bottomSheet.show(childFragmentManager, "tag")

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

        init()

        lifecycleScope.launch {
            if (uid != null) {
                viewModel.getPagingFlow(uid).collect {
                    noteApter.submitData(it)
                }
            }
        }




        viewModel.likePostStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                curLikedIndex?.let { index ->
                    noteApter.peek(index)?.isLiking = false
                    noteApter.notifyItemChanged(index)
                }
                snackBar(it)
            },
            onLoading = {
                curLikedIndex?.let { index ->
                    noteApter.peek(index)?.isLiking = true
                    noteApter.notifyItemChanged(index)
                }
            }
        ) { isLiked ->
            curLikedIndex?.let { index ->
                val uid = FirebaseAuth.getInstance().uid!!
                noteApter.peek(index)?.apply {
                    this.isLiked = isLiked
                    isLiking = false
                   /* if (isLiked) {
                        likedBy += uid
                    } else {
                        likedBy -= uid
                    }*/
                }
                noteApter.notifyItemChanged(index)
            }
        })

/*        viewModel.profileMeta.observe(viewLifecycleOwner, EventObserver(
            onError = {
                // binding.profileMetaProgressBar.isVisible = false
                snackBar(it)
            },
            //  onLoading = { binding.profileMetaProgressBar.isVisible = true }
        ) { user ->
            //  binding.profileMetaProgressBar.isVisible = false
            //   binding.tvUsername.text = user.username
            //    binding.tvProfileDescription.text = user.description
            //   glide.load(user.profilePictureUrl).into(binding.ivProfileImage)
        })*/
        viewModel.deleteNoteStatus.observe(viewLifecycleOwner, EventObserver(
            onError = { snackBar(it) },
            onLoading = {},
            onSuccess = {
                snackBar("deleted")
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
                // val cart = noteApter.differ.currentList[posotion]


                val cart = noteApter.peek(posotion)
                if (cart != null) {
                    viewModel.deleteNote(cart)
                }

                view?.let {

                  snackBar("Successfully Deleted")
                     /*   .apply {
                            setAction("Undo") {
                                viewModel.saveProduct(cart)

                            }
                            show()
                        }*/
                }

            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerview)
        }

        lifecycleScope.launch {
            if (uid != null) {
                viewModel.getPagingFlow(uid).collect {
                    noteApter.submitData(it)
                }
            }
        }

    }

    private fun setupRecyclerView() = binding.recyclerview.apply {
        adapter = noteApter
        layoutManager = GridLayoutManager(requireContext(),2)
    }


}