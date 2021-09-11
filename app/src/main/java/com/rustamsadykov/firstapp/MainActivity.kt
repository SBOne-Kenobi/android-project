package com.rustamsadykov.firstapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

//    private fun downloadBitmap(urlString: String): Bitmap? {
//        return try {
//            val url = URL(urlString)
//            val connection = url.openConnection() as HttpURLConnection
//            connection.doInput = true
//            connection.connect()
//            val stream = connection.inputStream
//            BitmapFactory.decodeStream(stream)
//        } catch (e: IOException) {
//            null
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.usersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

//        val bitmap = downloadBitmap("https://avatars.mds.yandex.net/get-pdb/1690495/7684cce3-d3a0-4b27-8d58-74ccc8cd2698/s1200")

        val adapter = UserAdapter()
        recyclerView.adapter = adapter
        adapter.userList = loadUsers()
//        adapter.testAvatar = bitmap
        adapter.notifyDataSetChanged()

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        getDrawable(R.drawable.divider_user)?.let { itemDecoration.setDrawable(it) }
        recyclerView.addItemDecoration(itemDecoration)
    }

    private fun loadUsers(): List<User> {
        val users = mutableListOf<User>()
        repeat(35) {
            users.add(User("User #${it}", "b${it / 10}", "bib"))
        }
        return users
    }

}