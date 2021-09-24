package com.example.todolist.data

import androidx.paging.PagingSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class NotesPagingSource(
    private val db: FirebaseFirestore,
    private val uid: String
) : PagingSource<QuerySnapshot, Note>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Note> {
        return try {
            val curPage = params.key ?: db.collection("notes")
                .whereEqualTo("authorUid", uid)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()

            val lastDocumentSnapshot = curPage.documents[curPage.size() - 1]

            val nextPage = db.collection("notes")
                .whereEqualTo("authorUid", uid)
                .orderBy("date", Query.Direction.DESCENDING)
                .startAfter(lastDocumentSnapshot)
                .get()
                .await()

            LoadResult.Page(
                curPage.toObjects(Note::class.java).onEach { post ->
                    val user = db.collection("users").document(uid).get().await().toObject(User::class.java)!!
                    post.date
                    post.authorUid
                    post.title
                    post.note
                },
                null,
                nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}