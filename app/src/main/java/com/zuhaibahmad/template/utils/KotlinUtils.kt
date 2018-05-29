

package com.zuhaibahmad.template.utils

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.support.v4.content.FileProvider
import android.support.v4.content.PermissionChecker.PERMISSION_GRANTED
import android.support.v4.util.PatternsCompat.EMAIL_ADDRESS
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DrawableUtils
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.View.MeasureSpec.UNSPECIFIED
import android.view.View.MeasureSpec.makeMeasureSpec
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.zuhaibahmad.template.AppConstants
import com.zuhaibahmad.template.R
import java.io.BufferedWriter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Zuhaib Ahmad on 6/27/2015.
 *
 *
 * Class carrying frequently used utility methods
 */
@Suppress("DEPRECATION")
@SuppressWarnings("unused", "MissingPermission", "WeakerAccess", "ResultOfMethodCallIgnored")


const val MAX_QUALITY = 100
const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

fun Context.getFullscreenDialog(root: ViewGroup? = null, view: View? = null, resId: Int): Dialog {
	val dialog = Dialog(this)
	val window = dialog.window
	if (window != null) {
		window.requestFeature(Window.FEATURE_NO_TITLE)
		when {
			root != null -> dialog.setContentView(root)
			view != null -> dialog.setContentView(view)
			resId > 0    -> dialog.setContentView(resId)
			else         -> {
				val container = RelativeLayout(this)
				container.layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
				dialog.setContentView(container)
			}
		}
		window.setLayout(MATCH_PARENT, WRAP_CONTENT)
	}
	return dialog
}

fun Context.getFullscreenDialog(): Dialog {
	// Layout container
	val root = RelativeLayout(this)
	root.layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)

	// Create fullscreen dialog
	val dialog = Dialog(this)
	val w = dialog.window
	if (w != null) {
		w.requestFeature(Window.FEATURE_NO_TITLE)
		dialog.setContentView(root)
		w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
	}
	return dialog
}

fun Context.getFullscreenDialog(v: View): Dialog {
	val dialog = Dialog(this)
	with(dialog.window) {
		requestFeature(Window.FEATURE_NO_TITLE)
		dialog.setContentView(v)
		setLayout(MATCH_PARENT, WRAP_CONTENT)
	}
	return dialog
}


fun showSnackbar(container: View, message: String, length: Int = Snackbar.LENGTH_SHORT) {
	Snackbar.make(
		container,    // Container
		message,    // Message to be displayed
		length      // Duration for which the message should be displayed
	).show()
}

//Show a custom alert dialog
fun showAlertDialogAndGoToLoginActivity(c: Context, title: String, message: String) {
	AlertDialog.Builder(c)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton(android.R.string.ok) { _, _ ->
			//Go to Login Activity
			val a = c as Activity
			//a.startActivity(new Intent(c, SplashActivity.class));
			a.finish()
		}.show()
}

fun Context.displayAlert(
	title: String,
	message: String,
	yes: Pair<Int, ((d: DialogInterface, id: Int) -> Unit)?> = Pair(android.R.string.yes, null),
	no: Pair<Int, ((d: DialogInterface, id: Int) -> Unit)?> = Pair(android.R.string.no, null),
	onCancelClick: ((d: DialogInterface) -> Unit)? = null
) = AlertDialog.Builder(this)
	.setTitle(title)
	.setMessage(message)
	.setPositiveButton(yes.first, yes.second)
	.setNegativeButton(no.first, no.second)
	.setOnCancelListener(onCancelClick)
	.show()

fun showAlertDialogWithoutCancel(c: Context, title: String, message: String) =
	AlertDialog.Builder(c)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton(android.R.string.ok, null)
		.show()

fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
	var cursor: Cursor? = null
	try {
		val proj = arrayOf(MediaStore.Images.Media.DATA)
		cursor = context.contentResolver.query(contentUri, proj, null, null, null)
		if (cursor != null) {
			val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
			cursor.moveToFirst()
			return cursor.getString(columnIndex)
		}
		return null
	} finally {
		if (cursor != null) {
			cursor.close()
		}
	}
}

fun getMultiStyleSignUpText(
	text: String,
	textColor: Int,
	range: IntRange,
	isBold: Boolean,
	isItalic: Boolean
): SpannableStringBuilder {
	val sb = SpannableStringBuilder(text)
	// Span to set text color to some RGB value
	val fcs = ForegroundColorSpan(textColor)
	val bss = if (isBold && isItalic) {
		// Span to make text bold and italic
		StyleSpan(Typeface.BOLD_ITALIC)
	} else if (isBold) {
		// Span to make text bold
		StyleSpan(Typeface.BOLD)
	} else if (isItalic) {
		// Span to make text bold
		StyleSpan(Typeface.ITALIC)
	} else {
		StyleSpan(Typeface.BOLD)
	}
	// Set the text color for first 4 characters
	sb.setSpan(fcs, range.start, range.endInclusive, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
	// make them also bold
	sb.setSpan(bss, range.start, range.endInclusive, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
	return sb
}

/**
 * get datetime
 */
val dateTime: String
	get() {
		val pattern = DATE_TIME_PATTERN
		return getDateFromPattern(pattern)
	}

private fun getDateFromPattern(pattern: String): String {
	val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
	val date = Date()
	return dateFormat.format(date)
}

/**
 * get datetime
 */
val time: String
	get() {
		val pattern = "HH:mm:ss"
		return getDateFromPattern(pattern)
	}

/**
 * get datetime in 12 hour format
 */
val timeAMPM: String
	get() {
		val pattern = "hh:mm aa"
		return getDateFromPattern(pattern)
	}

/**
 * get datetime
 */
val dateDDMMYYYYYFormat: String
	get() {
		val pattern = "dd-MM-yyyy"
		return getDateFromPattern(pattern)
	}

/**
 * get datetime
 */
@Throws(ParseException::class)
fun getDateInTFormatWithDayName(d: String): String {
	return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(
		SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault()).parse(d)
	)
}

/**
 * get datetime
 */
@Throws(ParseException::class)
fun getDateInTFormat(d: String): String {
	val pattern = "yyyy-MM-dd HH:mm:ss"
	val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
	val date = dateFormat.parse(d)
	return dateFormat.format(date).replace(" ", "T")
}


/**
 * get datetime
 */
@Throws(ParseException::class)
fun getDateFromTFormat(d: String): String {
	val pattern = "yyyy-MM-dd HH:mm:ss"
	val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
	val date = dateFormat.parse(d)
	return dateFormat.format(date).replace(" ", "T")
}

fun String.isValidEmail() = this.isNotEmpty() && EMAIL_ADDRESS.matcher(this).matches()

fun blobToBitmap(blob: ByteArray) = BitmapFactory.decodeByteArray(blob, 0, blob.size)

fun bitmapToBlob(bitmap: Bitmap): ByteArray {
	val bos = ByteArrayOutputStream()
	bitmap.compress(Bitmap.CompressFormat.PNG, MAX_QUALITY, bos)
	return bos.toByteArray() as ByteArray
}

fun String.capitalizeFirstLetter() =
	this.substring(0, 1).toUpperCase() + this.substring(1).toLowerCase()

fun Activity.hideStatusBar() {
	this.requestWindowFeature(Window.FEATURE_NO_TITLE)
	this.window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)
}

fun Activity.keepScreenOn() = this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

fun Context.displayShareImageDialog(screen: Bitmap) {
	val share = Intent(Intent.ACTION_SEND)
	share.type = "image/jpeg"
	val bytes = ByteArrayOutputStream()
	screen.compress(Bitmap.CompressFormat.JPEG, MAX_QUALITY, bytes)
	val f =
		File("${Environment.getExternalStorageDirectory()}" + File.separator + "temporary_file.jpg")
	try {
		f.createNewFile()
		val fo = FileOutputStream(f)
		fo.write(bytes.toByteArray())
	} catch (e: IOException) {
		e.printStackTrace()
	}
	val authority = "${applicationContext.packageName}.pdfprovider"
	val uri = FileProvider.getUriForFile(this, authority, f)
	share.putExtra(Intent.EXTRA_STREAM, uri)
	this.startActivity(Intent.createChooser(share, "Share Image"))
}


//Check if network is online
@SuppressLint("MissingPermission")
fun Context.isNetworkOnline(): Boolean {
	val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
	val netInfo = manager.activeNetworkInfo
	return netInfo != null && netInfo.isConnectedOrConnecting
}

fun String.removeLineBreaksAndTrim() = this.replace("[\\r\\n]".toRegex(), "").trim { it <= ' ' }

fun Context.appendLog(text: String, filename: String) {
	val dir = File(Environment.getExternalStorageDirectory(), "Logs")
	if (!dir.exists()) {
		dir.mkdirs()
	}

	val logFile = File(dir, filename)
	if (!logFile.exists()) {
		try {
			val isSuccess = logFile.createNewFile()
			Log.e(AppConstants.TAG, "Creation Success: " + isSuccess)
		} catch (e: IOException) {
			Log.e(AppConstants.TAG, e.message, e)
		}

	}
	try {
		//BufferedWriter for performance, true to set append to file flag
		val buf = BufferedWriter(FileWriter(logFile, true))
		buf.newLine()
		buf.append(Calendar.getInstance().time.toString()).append(" -> ").append(text)
		buf.newLine()
		buf.newLine()
		buf.close()
	} catch (e: IOException) {
		Log.e(AppConstants.TAG, e.message, e)
	}
}

@SuppressLint("NewApi")
fun String.copyToClipboard(c: Context) = try {
	val sdk = Build.VERSION.SDK_INT
	if (sdk < Build.VERSION_CODES.HONEYCOMB) {
		val clipboard = c.getSystemService(CLIPBOARD_SERVICE) as android.text.ClipboardManager
		clipboard.text = this
	} else {
		val clipboard = c.getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
		clipboard.primaryClip = ClipData.newPlainText("copy text", this)
	}
	true
} catch (e: IllegalStateException) {
	Log.e(AppConstants.TAG, e.message, e)
	false
}

/**
 * Return MD5 encrypted representation of given String
 */
fun md5(s: String): String {
	TODO("TO be implemented" + s)
}

const val TEXT_COLOR = 224
const val TEXT_SIZE = 14

fun DrawableUtils.drawTextToBitmap(context: Context, resId: Int, text: String): Bitmap {
	val resources = context.resources
	val scale = resources.displayMetrics.density
	var bitmap = BitmapFactory.decodeResource(resources, resId)

	var bitmapConfig: Bitmap.Config? = bitmap.config
	// set default bitmap config if none
	if (bitmapConfig == null) {
		bitmapConfig = Bitmap.Config.ARGB_8888
	}
	// resource bitmaps are immutable,
	// so we need to convert it to mutable one
	bitmap = bitmap.copy(bitmapConfig, true)

	val canvas = Canvas(bitmap)
	// new anti-alised Paint
	val paint = Paint(Paint.ANTI_ALIAS_FLAG)
	// text color - #3D3D3D
	paint.color = Color.rgb(TEXT_COLOR, TEXT_COLOR, TEXT_COLOR)
	// text size in pixels
	paint.textSize = (TEXT_SIZE * scale).toInt().toFloat()
	// text shadow
	paint.setShadowLayer(1f, 0f, 1f, Color.WHITE)

	// draw text to the Canvas center
	val bounds = Rect()
	paint.getTextBounds(text, 0, text.length, bounds)
	val x = (bitmap.width - bounds.width()) / 2
	val y = (bitmap.height + bounds.height()) / 2

	canvas.drawText(text, x.toFloat(), y.toFloat(), paint)

	return bitmap
}

/**
 * @return the last know best location
 */
@SuppressLint("MissingPermission")
fun getLastBestLocation(c: Context, locManager: LocationManager): Location? {
	if (checkSelfPermission(c, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED &&
		checkSelfPermission(c, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED
	) {

		val locationGPS = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
		val locationNet = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

		var gpsLocationTime: Long = 0
		if (locationGPS != null) gpsLocationTime = locationGPS.time

		var netLocationTime: Long = 0
		if (locationNet != null) netLocationTime = locationNet.time

		return if (0 < gpsLocationTime - netLocationTime) locationGPS else locationNet
	}
	return null
}

fun moveFile(inputPath: String, inputFile: String, outputPath: String) {
	TODO("TO be implemented" + inputPath + inputFile + outputPath)
}

const val MB = 1048576f

fun bytesToMB(bytes: Long) = bytes / MB

@Throws(IOException::class)
fun Any.serialize(): ByteArray {
	val out = ByteArrayOutputStream()
	val os = ObjectOutputStream(out)
	os.writeObject(this)
	return out.toByteArray()
}

@Throws(IOException::class, ClassNotFoundException::class)
fun ByteArray.deserialize() = ObjectInputStream(ByteArrayInputStream(this)).readObject()

/**
 * Reads the given [File Stream][InputStream] and produces string from file content

 * @param stream The provided File Stream
 * *
 * @return String representation of file contents
 */
fun readFromFile(stream: InputStream): String {
	TODO("TO be implemented" + stream)
}

/**
 * Writes the given text to a temporary file and returns it

 * @param text The text to be written to the file
 * *
 * @return file
 */
fun String.writeToFile(): File {
	val f = File("temp.txt")
	if (!f.exists()) {
		try {
			val isSuccess = f.createNewFile()
			val errorMessage = "File Creation Success: $isSuccess"
			Log.e(AppConstants.TAG, errorMessage)
		} catch (e: IOException) {
			Log.e(AppConstants.TAG, e.message, e)
		}
	}

	try {
		//BufferedWriter for performance, true to set append to file flag
		val buf = BufferedWriter(FileWriter(f, true))
		buf.append(Calendar.getInstance().time.toString()).append(" -> ").append(this)
		buf.newLine()
		buf.newLine()
		buf.close()
	} catch (e: IOException) {
		Log.e(AppConstants.TAG, e.message, e)
	}

	return f
}

fun ViewGroup.showLightSnackBar(message: String) {
	val s = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
	val v = s.view.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
	v.setTextColor(Color.WHITE)
	s.show()
}

/**
 * get datetime
 */
val date: String = getDateFromPattern("DD yyyy-MM-dd")

fun String.appendLog() {
	val logFile = File("sdcard/" + AppConstants.LOG_FILE_NAME)
	if (!logFile.exists()) {
		try {
			logFile.createNewFile()
		} catch (e: IOException) {
			Log.e(AppConstants.TAG, e.message, e)
		}
	}
	try {
		//BufferedWriter for performance, true to set append to file flag
		val buf = BufferedWriter(FileWriter(logFile, true))
		buf.append(Calendar.getInstance().time.toString()).append(" -> ").append(this)
		buf.newLine()
		buf.newLine()
		buf.close()
	} catch (e: IOException) {
		Log.e(AppConstants.TAG, e.message, e)
	}
}

fun View.viewToDrawable(): Drawable {
	this.isDrawingCacheEnabled = true

	// this is the important code :)
	// Without it the view will have a dimension of 0,0 and the bitmap will be null
	this.measure(makeMeasureSpec(0, UNSPECIFIED), makeMeasureSpec(0, UNSPECIFIED))
	this.layout(0, 0, this.measuredWidth, this.measuredHeight)

	this.buildDrawingCache(true)
	val b = Bitmap.createBitmap(this.drawingCache)
	this.isDrawingCacheEnabled = false // clear drawing cache
	return BitmapDrawable(this.context.resources, b)
}

fun Activity.displayShareDialog(message: String) {
	//Share the text
	val sendIntent = Intent()
	sendIntent.action = Intent.ACTION_SEND
	sendIntent.putExtra(Intent.EXTRA_TEXT, message)
	sendIntent.type = "text/plain"
	this.startActivity(sendIntent)
}

//Show a custom alert dialog
fun Context.showAlertDialog(title: String, message: String) =
	AlertDialog.Builder(this).setTitle(title).setMessage(message).show()

/**
 * @param bitmap the bitmap to be converted
 * *
 * @return converting bitmap and return a string
 */
fun DrawableUtils.bitMapToString(bitmap: Bitmap): String {
	val baos = ByteArrayOutputStream()
	bitmap.compress(Bitmap.CompressFormat.JPEG, (MAX_QUALITY / 2).toInt(), baos)
	val b = baos.toByteArray()
	return Base64.encodeToString(b, Base64.URL_SAFE or Base64.NO_WRAP)
}

/**
 * @param encodedString The string to be decoded
 * *
 * @return bitmap (from given string)
 */
fun DrawableUtils.stringToBitMap(encodedString: String): Bitmap? = try {
	val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
	BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
} catch (e: IllegalStateException) {
	Log.e(AppConstants.TAG, e.message, e)
	null
}

fun Activity.hideKeyboard() {
	val inputMethodManager = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
	val view = this.currentFocus
	if (view != null) inputMethodManager.hideSoftInputFromWindow(view.windowToken, HIDE_NOT_ALWAYS)
}

const val MONTHS_IN_YEAR = 12

fun getMonthForInt(num: Int) =
	if (num in 0 until MONTHS_IN_YEAR) DateFormatSymbols().months[num]
	else throw IllegalArgumentException("Invalid month")

fun parseDateAndTime(dateString: String): Date {
	val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
	var date: Date? = null
	try {
		date = format.parse(dateString)
	} catch (e: ParseException) {
		Log.e(AppConstants.TAG, e.message, e)
	}

	return date as Date
}

fun parseDateOnly(dateString: String): Date {
	val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
	var date: Date? = null
	try {
		date = formatter.parse(dateString)
	} catch (e: ParseException) {
		Log.e(AppConstants.TAG, e.message, e)
	}

	return date as Date
}

fun dateSlashToDash(dateString: String): String {
	val format1 = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
	val format2 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
	var date: Date? = null
	try {
		date = format1.parse(dateString)
	} catch (e: ParseException) {
		Log.e(AppConstants.TAG, e.message, e)
	}

	return format2.format(date)
}

fun Any.objectToStream(): ByteArray {
	val bos = ByteArrayOutputStream()

	val os: ObjectOutputStream
	try {
		os = ObjectOutputStream(bos)
		os.writeObject(this)
		os.close()
	} catch (e: IOException) {
		Log.e(AppConstants.TAG, e.message, e)
	}

	return bos.toByteArray()
}

/**
 * Return sha512 encrypted representation of given String
 */
@Throws(NoSuchAlgorithmException::class)
fun sha512(passwordToHash: String): String {
	val md = MessageDigest.getInstance("SHA-512")
	md.update(passwordToHash.toByteArray())
	val bytes = md.digest()
	var encryptedString = ""
	for (temp in bytes) {
		var s = Integer.toHexString(temp.toInt())
		while (s.length < 2) {
			s = "0$s"
		}
		s = s.substring(s.length - 2)
		encryptedString += s
	}

	return encryptedString
}

/*fun isGooglePlayServicesAvailable(Activity context):Boolean {
	GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
	int status = googleApiAvailability.isGooglePlayServicesAvailable(context);
	if(status != ConnectionResult.SUCCESS) {
		if(googleApiAvailability.isUserResolvableError(status)) {
			googleApiAvailability.getErrorDialog(context, status, 2404).show();
		}
		return false;
	}
	return true;
}*/

fun Context.buildAlertMessageNoGps() = AlertDialog.Builder(this)
	.setMessage("Your GPS seems to be disabled, do you want to enable it?")
	.setCancelable(false)
	.setPositiveButton("Yes") { _, _ ->
		this.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
	}
	.setNegativeButton("No") { dialog, _ -> dialog.cancel() }
	.create()
	.show()

fun Context.hideSoftKeyboard(view: View) {
	val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
}

fun EditText.disable() {
	this.isFocusable = false
	this.isFocusableInTouchMode = false
	this.isClickable = false
}

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
fun Context.getRoundIconView(imageID: Int, tintColor: Int): Drawable {
	val icon = FloatingActionButton(this)
	icon.imageTintList = ColorStateList.valueOf(tintColor)
	val d = ContextCompat.getDrawable(this, imageID)
	d?.setTint(tintColor)
	icon.setImageDrawable(d)

	return icon.viewToDrawable()
}
