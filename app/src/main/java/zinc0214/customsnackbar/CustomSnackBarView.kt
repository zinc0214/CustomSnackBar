package zinc0214.customsnackbar

import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.ContentViewCallback

class CustomSnackBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    lateinit var msg: TextView
    lateinit var action: TextView
    lateinit var cardView: CardView

    init {
        View.inflate(context, R.layout.custom_snack_bar_view, this)
        clipToPadding = false
        this.msg = findViewById(R.id.message)
        this.action = findViewById(R.id.action)
        this.cardView = findViewById(R.id.cardView)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        val animatorSet = AnimatorSet().apply {
            interpolator = OvershootInterpolator()
            setDuration(500)

        }
        animatorSet.start()
    }

    override fun animateContentOut(delay: Int, duration: Int) {
    }
}