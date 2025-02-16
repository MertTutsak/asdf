package com.example.recyclerview_item_animation.ui.viewpager.direction

import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.example.recyclerview_item_animation.ui.viewpager.item.ItemViewHolder
import com.example.recyclerview_item_animation.ui.viewpager.base.BaseViewPager
import java.util.ArrayList

abstract class DirectionPagerTransformer : ViewPager.PageTransformer {
    var holderList = ArrayList<DirectionViewHolder>()

    abstract fun onTransform(page: View, position: Float)

    override fun transformPage(page: View, position: Float) {
        setDirection(getHolder(page), position)
        onTransform(page, position)
        addHolder(getHolder(page))
    }

    open class DirectionViewHolder(view: View) : BaseViewPager.ViewHolder(view) {

        var direction: ViewPagerDirection =
            ViewPagerDirection.NONE

        var state: ViewPagerState =
            ViewPagerState.NONE

        override fun bind(baseViewModel: BaseViewModel, position: Int) {
        }

        fun setDirection(position: Float) {
            if (this.lastPosition == position) {
                this.state == ViewPagerState.STABLE
                return
            }

            if (position <= -1f) {
                this.direction = ViewPagerDirection.ON_LEFT
                this.state == ViewPagerState.STABLE
            } else if (position >= 1f) {
                this.direction = ViewPagerDirection.ON_RIGHT
                this.state == ViewPagerState.STABLE
            } else if (position < 0) {
                if (position > this.lastPosition) {
                    this.direction = ViewPagerDirection.FROM_LEFT
                    this.state == ViewPagerState.MOVE
                } else if (position < this.lastPosition) {
                    this.direction = ViewPagerDirection.TO_LEFT
                    this.state == ViewPagerState.MOVE
                }
            } else if (position > 0) {
                if (position >= this.lastPosition) {
                    this.direction = ViewPagerDirection.TO_RIGHT
                    this.state == ViewPagerState.MOVE
                } else if (position < this.lastPosition) {
                    this.direction = ViewPagerDirection.FROM_RIGHT
                    this.state == ViewPagerState.MOVE
                }
            } else {
                this.direction = ViewPagerDirection.CENTER
                this.state == ViewPagerState.STABLE
            }
            this.lastPosition = position
        }

        var lastPosition: Float = -1f
    }

    protected fun getHolder(page: View): DirectionViewHolder {
        holderList.forEach {
            if (it.view == page) {
                return it
            }
        }

        return ItemViewHolder(page)
    }

    protected fun addHolder(holder: DirectionViewHolder) {
        if (!holderList.contains(holder)) {
            holderList.add(holder)
        }
    }

    protected fun setDirection(holder: DirectionViewHolder, position: Float) {
        if (holder.lastPosition == position) {
            holder.state == ViewPagerState.STABLE
            return
        }

        if (position <= -1f) {
            holder.direction = ViewPagerDirection.ON_LEFT
            holder.state == ViewPagerState.STABLE
        } else if (position >= 1f) {
            holder.direction = ViewPagerDirection.ON_RIGHT
            holder.state == ViewPagerState.STABLE
        } else if (position < 0) {
            if (position > holder.lastPosition) {
                holder.direction = ViewPagerDirection.FROM_LEFT
                holder.state == ViewPagerState.MOVE
            } else if (position < holder.lastPosition) {
                holder.direction = ViewPagerDirection.TO_LEFT
                holder.state == ViewPagerState.MOVE
            }
        } else if (position > 0) {
            if (position >= holder.lastPosition) {
                holder.direction = ViewPagerDirection.TO_RIGHT
                holder.state == ViewPagerState.MOVE
            } else if (position < holder.lastPosition) {
                holder.direction = ViewPagerDirection.FROM_RIGHT
                holder.state == ViewPagerState.MOVE
            }
        } else {
            holder.direction = ViewPagerDirection.CENTER
            holder.state == ViewPagerState.STABLE
        }
        holder.lastPosition = position
    }

    enum class ViewPagerState {
        STABLE,
        MOVE,
        NONE
    }

    enum class ViewPagerDirection {
        TO_LEFT,
        ON_LEFT,
        FROM_LEFT,
        TO_RIGHT,
        FROM_RIGHT,
        ON_RIGHT,
        CENTER,
        NONE
    }
}