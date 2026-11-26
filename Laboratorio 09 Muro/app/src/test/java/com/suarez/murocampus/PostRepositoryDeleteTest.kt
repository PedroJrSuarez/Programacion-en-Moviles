package com.suarez.murocampus

import com.suarez.murocampus.data.PostRepositoryRemote
import com.suarez.murocampus.data.remote.PostApi
import com.suarez.murocampus.model.Comentario
import com.suarez.murocampus.model.NuevoPost
import com.suarez.murocampus.model.Post
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class PostRepositoryDeleteTest {
    @Test
    fun successfulDeleteReturnsSuccess() = runBlocking {
        val result = PostRepositoryRemote(FakePostApi(Response.success(Unit))).eliminar(1)

        assertTrue(result.isSuccess)
    }

    @Test
    fun unsuccessfulDeleteReturnsHttpFailure() = runBlocking {
        val response = Response.error<Unit>(
            500,
            "{}".toResponseBody()
        )
        val result = PostRepositoryRemote(FakePostApi(response)).eliminar(1)

        assertTrue(result.isFailure)
        assertEquals(500, (result.exceptionOrNull() as HttpException).code())
    }

    private class FakePostApi(
        private val deleteResponse: Response<Unit>
    ) : PostApi {
        override suspend fun listar(): List<Post> = emptyList()

        override suspend fun detalle(id: Int): Post =
            error("Not used in this test")

        override suspend fun comentarios(id: Int): List<Comentario> = emptyList()

        override suspend fun porUsuario(userId: Int): List<Post> = emptyList()

        override suspend fun crear(nuevo: NuevoPost): Post =
            error("Not used in this test")

        override suspend fun eliminar(id: Int): Response<Unit> = deleteResponse
    }
}
