package com.example.imagesearchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchapp.data.Repository
import com.example.imagesearchapp.databinding.ActivityMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    // 코루틴 스코프 정의
    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        fetchRandomPhotos()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    fun initViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = PhotoAdapter()

    }

    private fun fetchRandomPhotos(query: String? = null) = scope.launch {
        Repository.getRandomPhotos(query)?.let { photos ->
            (binding.recyclerView.adapter as? PhotoAdapter)?.apply {
                this.photos = photos
                notifyDataSetChanged()
            }
        }
    }
}