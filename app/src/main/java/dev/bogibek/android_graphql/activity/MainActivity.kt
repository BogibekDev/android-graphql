package dev.bogibek.android_graphql.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import dev.bogibek.android_graphql.UserAdapter
import dev.bogibek.android_graphql.UsersListQuery
import dev.bogibek.android_graphql.databinding.ActivityMainBinding
import dev.bogibek.android_graphql.network.GraphQL
import dev.bogibek.android_graphql.utils.launchActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val userAdapter by lazy { UserAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()

    }

    private fun initViews() {
        binding.apply {
            rvMain.adapter = userAdapter
            fb.setOnClickListener {
                launchActivity(CreateActivity::class.java)
            }

        }
        userAdapter.itemClick = {
            val intent = Intent(this@MainActivity, UpdateActivity::class.java)
            intent.putExtra("id", it.id.toString())
            startActivity(intent)
        }
        getUserList()
    }

    private fun getUserList() {
        lifecycleScope.launch launchWhenResume@{
            val response = try {
                GraphQL.get().query(UsersListQuery(20)).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResume
            }
            val users = response.data?.users
            userAdapter.submitList(users?.toList())
        }
    }

    override fun onRestart() {
        super.onRestart()
        getUserList()
    }
}