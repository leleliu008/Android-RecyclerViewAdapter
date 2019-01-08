package com.fpliu.newton.ui.recyclerview.sample

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fpliu.newton.ui.base.BaseActivity
import com.fpliu.newton.ui.recyclerview.adapter.ItemAdapter
import com.fpliu.newton.ui.recyclerview.holder.ItemViewHolder
import kotlinx.android.synthetic.main.activity_main.*

/**
 * RecyclerViewHelper使用示例
 * @author 792793182@qq.com 2018-03-28.
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "RecyclerViewHelper使用示例"

        setContentView(R.layout.activity_main)

        val items = ArrayList<Pair<Int, String>>().apply {
            repeat(10) {
                add(Pair(it, it.toString()))
            }
        }
        recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            adapter = object : ItemAdapter<Pair<Int, String>>(items) {
                override fun onBindLayout(parent: ViewGroup, viewType: Int): Int {
                    return R.layout.item
                }

                override fun onBindViewHolder(holder: ItemViewHolder, position: Int, item: Pair<Int, String>) {
                    holder.run {
                        id(R.id.imageView).image("https://modao.cc/uploads/avatars/33224/profile_user-avatar.png", R.drawable.default_img)
                        id(R.id.textView).text(item.second)
                    }
                }
            }
        }
    }
}