package com.finance.plutus.mobile.app.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.finance.plutus.mobile.R
import java.util.*


/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
@SuppressLint("ClickableViewAccessibility")
abstract class SwipeHelper(
    private val recyclerView: RecyclerView
) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.ACTION_STATE_IDLE,
    ItemTouchHelper.LEFT
) {

    companion object {
        private const val BUTTON_WIDTH = 300
    }

    private var swipedPosition = -1
    private val buttonsBuffer: MutableMap<Int, List<UnderlayButton>> = mutableMapOf()
    private val recoverQueue = object : LinkedList<Int>() {
        override fun add(element: Int): Boolean {
            if (contains(element)) return false
            return super.add(element)
        }
    }

    private val touchListener = View.OnTouchListener { _, event ->
        if (swipedPosition < 0) return@OnTouchListener false
        buttonsBuffer[swipedPosition]?.forEach { it.handle(event) }
        recoverQueue.add(swipedPosition)
        swipedPosition = -1
        recoverSwipedItem()
        true
    }

    init {
        recyclerView.setOnTouchListener(touchListener)
    }

    private fun recoverSwipedItem() {
        while (!recoverQueue.isEmpty()) {
            val position = recoverQueue.poll() ?: return
            recyclerView.adapter?.notifyItemChanged(position)
        }
    }

    private fun drawButtons(
        canvas: Canvas,
        buttons: List<UnderlayButton>,
        itemView: View,
        dX: Float
    ) {
        var right = itemView.right
        buttons.forEach { button ->
            val width = (-1) * dX / buttons.size
            val left = right - width
            button.draw(
                canvas,
                RectF(left, itemView.top.toFloat(), right.toFloat(), itemView.bottom.toFloat())
            )

            right = left.toInt()
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val position = viewHolder.bindingAdapterPosition
        var maxDX = dX
        val itemView = viewHolder.itemView

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                if (!buttonsBuffer.containsKey(position)) {
                    buttonsBuffer[position] = instantiateUnderlayButton(position)
                }

                val buttons = buttonsBuffer[position] ?: return
                if (buttons.isEmpty()) return
                maxDX = dX * buttons.size * BUTTON_WIDTH / itemView.width
                drawButtons(c, buttons, itemView, maxDX)
            }
        }

        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            maxDX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.bindingAdapterPosition
        if (swipedPosition != position) recoverQueue.add(swipedPosition)
        swipedPosition = position
        recoverSwipedItem()
    }

    abstract fun instantiateUnderlayButton(position: Int): List<UnderlayButton>

    interface UnderlayButtonClickListener {
        fun onClick()
    }

    class UnderlayButton(
        private val context: Context,
        @DrawableRes private val drawableRes: Int,
        @ColorRes private val colorBackground: Int,
        private val clickListener: UnderlayButtonClickListener
    ) {
        private var clickableRegion: RectF? = null

        fun draw(canvas: Canvas, rect: RectF) {
            val paint = Paint()

            paint.color = ContextCompat.getColor(context, colorBackground)
            canvas.drawRect(rect, paint)

            val bitmap = AppCompatResources.getDrawable(
                context,
                drawableRes
            )?.toBitmap()
            bitmap?.let {
                canvas.drawBitmap(
                    bitmap,
                    rect.centerX() - bitmap.width / 2,
                    rect.centerY() - bitmap.height / 2,
                    paint
                )
            }

            clickableRegion = rect
        }

        fun handle(event: MotionEvent) {
            clickableRegion?.let {
                if (it.contains(event.x, event.y)) {
                    clickListener.onClick()
                }
            }
        }
    }
}

private fun Drawable.toBitmap(): Bitmap? {
    if (this is BitmapDrawable) {
        return this.bitmap
    }
    val bitmap =
        Bitmap.createBitmap(
            this.intrinsicWidth,
            this.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
    val canvas = Canvas(bitmap)
    this.setBounds(0, 0, canvas.width, canvas.height)
    this.draw(canvas)
    return bitmap
}

object Buttons {
    fun deleteButton(context: Context, callback: Runnable): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            context,
            R.drawable.ic_baseline_delete_24,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    callback.run()
                }
            })
    }

    fun editButton(context: Context, callback: Runnable): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            context,
            R.drawable.ic_baseline_edit_24,
            android.R.color.holo_orange_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    callback.run()
                }
            })
    }
}