package com.suarez.murocampus

import com.suarez.murocampus.model.Post
import com.suarez.murocampus.viewmodel.filtrarPorTitulo
import org.junit.Assert.assertEquals
import org.junit.Test

class PostsSearchTest {
    private val posts = listOf(
        Post(userId = 1, id = 1, title = "Compose with Kotlin", body = ""),
        Post(userId = 2, id = 2, title = "Android networking", body = "")
    )

    @Test
    fun searchMatchesTitleIgnoringCaseAndWhitespace() {
        assertEquals(listOf(posts[0]), filtrarPorTitulo(posts, "  KOTLIN "))
    }

    @Test
    fun blankSearchReturnsAllPosts() {
        assertEquals(posts, filtrarPorTitulo(posts, "   "))
    }

    @Test
    fun searchDoesNotMatchPostBody() {
        val post = Post(userId = 1, id = 3, title = "Networking", body = "Kotlin")
        assertEquals(emptyList<Post>(), filtrarPorTitulo(listOf(post), "Kotlin"))
    }
}
