package zinc0214.customsnackbar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar

class CustomSnackBarWidget(
    parent: ViewGroup,
    content: CustomSnackBarView
) : BaseTransientBottomBar<CustomSnackBarWidget>(parent, content, content) {

    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        getView().setPadding(0, 0, 0, 0)
    }

    companion object {

        fun make(
            view: View,
            message: String, act: String, radius: Int,
            bgColor: Int, msgColor: Int, actColor: Int, duration: Int,
            listener: View.OnClickListener?, bottomMargin :Int =0
        ): CustomSnackBarWidget? {

            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )

            try {
                val customView = LayoutInflater.from(view.context).inflate(
                    R.layout.custom_snack_view_widget,
                    parent,
                    false
                ) as CustomSnackBarView
                customView.apply {
                    msg.text = message
                    action.text = act
                    msg.setTextColor(msgColor)
                    action.setTextColor(actColor)
                    cardView.radius = radius.toFloat()
                    cardView.setCardBackgroundColor(bgColor)
                    action.setOnClickListener { listener }

                    val layoutParams = customView.layoutParams
                    if (layoutParams is MarginLayoutParams) {
                        layoutParams.bottomMargin = bottomMargin
                    }

                }

                return CustomSnackBarWidget(
                    parent,
                    customView
                ).setDuration(duration)
            } catch (e: Exception) {
                Log.v("exception ", e.message)
            }

            return null
        }


        private fun View?.findSuitableParent(): ViewGroup? {
            var view = this
            var fallback: ViewGroup? = null
            do {
                if (view is CoordinatorLayout) {
                    // We've found a CoordinatorLayout, use it
                    return view
                } else if (view is FrameLayout) {
                    if (view.id == android.R.id.content) {
                        // If we've hit the decor content view, then we didn't find a CoL in the
                        // hierarchy, so use it.
                        return view
                    } else {
                        // It's not the content view but we'll use it as our fallback
                        fallback = view
                    }
                }

                if (view != null) {
                    // Else, we will loop and crawl up the view hierarchy and try to find a parent
                    val parent = view.parent
                    view = if (parent is View) parent else null
                }
            } while (view != null)

            // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
            return fallback
        }
    }

}