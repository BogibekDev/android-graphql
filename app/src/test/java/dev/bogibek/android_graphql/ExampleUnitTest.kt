package dev.bogibek.android_graphql

import dev.bogibek.android_graphql.network.GraphQL
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkUsersList() = runTest {
        val response = GraphQL.get().query(UsersListQuery(10)).execute()
        assertEquals(10, response.data?.users?.size)
    }

    @Test
    fun `check inserting successful if 1 is successful`() = runTest {
        val response =
            GraphQL.get().mutation(InsertUserMutation("Alisher", "Killer", "killer@dev")).execute()
        assertEquals(1, response.data?.insert_users?.affected_rows)
    }

    @Test
    fun `checking for delete user if response 1 is true`()= runTest {
       val response =
           GraphQL.get().mutation(DeleteUserMutation("cfd36553-a552-474c-a006-6fbb3dee5ae5"))
               .execute()
        assertEquals(1,response.data?.delete_users?.affected_rows)
    }

}