package zinc0214.customsnackbar

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import zinc0214.customsnackbar.databinding.ActivityMainBinding
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.showBasicSnackBar.setOnClickListener { l -> showBasicSnackBar(l) }
        binding.showCustomSnackBar.setOnClickListener { l -> showCustomSnackBar(l) }
    }

    private fun showBasicSnackBar(view: View) {
        val message = binding.messageEditText.text("Message")
        val action = binding.actionEditText.text("Action")
        val bgColor = binding.backgroundColor.colorText("#ffffff").parseColor()
        val msgColor = binding.messageTextColor.colorText("#000000").parseColor()
        val actionColor = binding.actionTextColor.colorText("#000000").parseColor()

        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.apply {
            val textView = this.view.findViewById(R.id.snackbar_text) as TextView
            textView.setTextColor(msgColor)
            this.view.setBackgroundColor(bgColor)
            setActionTextColor(actionColor)
            setAction(action) {
                fun onClick(v: View) {

                }
            }.show()
        }
    }

    private fun showCustomSnackBar(view: View) {
        val message = binding.messageEditText.text("Message")
        val action = binding.actionEditText.text("Action")
        val radius = binding.radiusEditText.text("30").toInt()
        val bgColor = binding.backgroundColor.colorText("#ffffff").parseColor()
        val msgColor = binding.messageTextColor.colorText("#000000").parseColor()
        val actionColor = binding.actionTextColor.colorText("#000000").parseColor()
        val bottomMargin = binding.bottomMarginEditText.text("0").toInt()

        val listener = View.OnClickListener { }
        CustomSnackBarWidget.make(
            view, message, action, radius, bgColor, msgColor, actionColor, 1000, listener, bottomMargin
        )?.show()
    }

    private fun EditText.text(defString: String) =
        if (this.text.isNotBlank()) this.text.toString() else defString

    private fun EditText.colorText(defColor: String): CharSequence {
        return if (this.text.isNotBlank()) {
            val colorPattern =
                Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})")
            val m: Matcher = colorPattern.matcher(this.text)
            if (m.matches()) this.text else defColor
        } else {
            defColor
        }
    }

    private fun CharSequence.parseColor() = Color.parseColor(this.toString())
}
