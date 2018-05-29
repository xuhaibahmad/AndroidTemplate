package ae.innovativesolutions.wisdomhours.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static android.text.TextUtils.isEmpty;

/**
 * Created by Zuhaib Ahmad on 6/27/2015.
 * <p>
 * Class carrying frequently used utility methods
 */
@SuppressWarnings({"unused", "MissingPermission", "WeakerAccess", "ResultOfMethodCallIgnored"})
public class Utils {

	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static Dialog getFullscreenDialog(Context c) {
		// Layout container
		final RelativeLayout root = new RelativeLayout(c);
		root.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		// Create fullscreen dialog
		final Dialog dialog = new Dialog(c);
		Window w = dialog.getWindow();
		if (w != null) {
			w.requestFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(root);
			w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		return dialog;
	}

	public static Dialog getFullscreenDialog(Context c, int layoutID) {
		final Dialog dialog = new Dialog(c);
		Window w = dialog.getWindow();
		if (w != null) {
			w.requestFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(layoutID);
			w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		return dialog;
	}

	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	public static int getScreenDensityInDpi(Context context) {
		return (int)(getScreenDensity(context) * 160f);
	}


	public static Dialog getFullscreenDialog(Context c, View v) {
		final Dialog dialog = new Dialog(c);
		Window w = dialog.getWindow();
		if (w != null) {
			w.requestFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(v);
			w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		return dialog;
	}

	public static void showSnackbar(View container, String message) {
		Snackbar.make(
				container,                  // Container
				message,                    // Message to be displayed
				Snackbar.LENGTH_SHORT       // Duration for which the message should be displayed
		).show();
	}

	//Show a custom alert dialog
	public static void showAlertDialogAndGoToLoginActivity(final Context c, String title, String message) {
		new AlertDialog.Builder(c)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(android.R.string.ok, (dialog, which) -> {
					//Go to Login Activity
					Activity a = (Activity) c;
					//a.startActivity(new Intent(c, SplashActivity.class));
					a.finish();
				}).show();
	}

	public static void showAlertDialogWithoutCancel(final Context c, String title, String message) {
		new AlertDialog.Builder(c)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(android.R.string.ok, null)
				.show();
	}

	public static String getRealPathFromURI(Context context, Uri contentUri) {
		Cursor cursor = null;
		try {
			String[] proj = {MediaStore.Images.Media.DATA};
			cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
			if (cursor != null) {
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				return cursor.getString(column_index);
			}
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static SpannableStringBuilder getMultiStyleSignUpText(String text, int textColor,
																 int startIndext, int endIndex,
																 boolean isBold, boolean isItalic) {
		SpannableStringBuilder sb = new SpannableStringBuilder(text);
		// Span to set text color to some RGB value
		ForegroundColorSpan fcs = new ForegroundColorSpan(textColor);
		StyleSpan bss;
		if (isBold && isItalic) {
			// Span to make text bold and italic
			bss = new StyleSpan(Typeface.BOLD_ITALIC);
		} else if (isBold) {
			// Span to make text bold
			bss = new StyleSpan(Typeface.BOLD);
		} else if (isItalic) {
			// Span to make text bold
			bss = new StyleSpan(Typeface.ITALIC);
		} else {
			bss = new StyleSpan(Typeface.BOLD);
		}
		// Set the text color for first 4 characters
		sb.setSpan(fcs, startIndext, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		// make them also bold
		sb.setSpan(bss, startIndext, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		return sb;
	}

	/**
	 * get datetime
	 */
	public static String getDateTime() {
		String pattern = DATE_TIME_PATTERN;
		return getDateFromPattern(pattern);
	}

	private static String getDateFromPattern(String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * get datetime
	 */
	public static String getTime() {
		String pattern = "HH:mm:ss";
		return getDateFromPattern(pattern);
	}

	/**
	 * get datetime in 12 hour format
	 */
	public static String getTimeAMPM() {
		String pattern = "hh:mm aa";
		return getDateFromPattern(pattern);
	}

	/**
	 * get datetime
	 */
	public static String getDateDDMMYYYYYFormat() {
		String pattern = "dd-MM-yyyy";
		return getDateFromPattern(pattern);
	}

	/**
	 * get datetime
	 */
	public static String getDateInTFormatWithDayName(String d) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(
				new SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault()).parse(d)
		);
	}

	/**
	 * get datetime
	 */
	public static String getDateInTFormat(String d) throws ParseException {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
		Date date = dateFormat.parse(d);
		return dateFormat.format(date).replace(" ", "T");
	}


	/**
	 * get datetime
	 */
	public static String getDateFromTFormat(String d) throws ParseException {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
		Date date = dateFormat.parse(d);
		return dateFormat.format(date).replace(" ", "T");
	}

	public static boolean isValidEmail(String email) {
		return !isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	public static Bitmap blobToBitmap(byte[] blob) {
		return (blob != null) ? BitmapFactory.decodeByteArray(blob, 0, blob.length) : null;
	}

	public static byte[] bitmapToBlob(Bitmap bitmap) {
		byte[] bytes = null;
		if (bitmap != null) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
			bytes = bos.toByteArray();
		}
		return bytes;
	}

	public static String capitalizeFirstLetter(String word) {
		return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
	}

	public static void hideStatusBar(Context c) {
		// remove title
		Activity a = (Activity) c;
		a.requestWindowFeature(Window.FEATURE_NO_TITLE);
		a.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	//Check if network is online
	public static boolean isNetworkOnline(Context c) {
		ConnectivityManager connectivityManager = (ConnectivityManager)
				c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}

	public static String removeLineBreaksAndTrim(String text) {
		return text.replaceAll("\\r|\\n", "").trim();
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static void appendLog(String text, String filename) {
		File dir = new File(Environment.getExternalStorageDirectory(), "Logs");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File logFile = new File(dir, filename);
		if (!logFile.exists()) {
			try {
				boolean isSuccess = logFile.createNewFile();
				Log.e(AppConstants.TAG, "Creation Success: " + isSuccess);
			} catch (IOException e) {
				Log.e(AppConstants.TAG, e.getMessage(), e);
			}
		}
		try {
			//BufferedWriter for performance, true to set append to file flag
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
			buf.newLine();
			buf.append(Calendar.getInstance().getTime().toString()).append(" -> ").append(text);
			buf.newLine();
			buf.newLine();
			buf.close();
		} catch (IOException e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
		}
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static boolean copyToClipboard(Context context, String text) {
		try {
			int sdk = Build.VERSION.SDK_INT;
			if (sdk < Build.VERSION_CODES.HONEYCOMB) {
				android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
						context.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboard.setText(text);
			} else {
				android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
						context.getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData clip = ClipData.newPlainText("copy text", text);
				clipboard.setPrimaryClip(clip);
			}
			return true;
		} catch (Exception e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Return MD5 encrypted representation of given String
	 */
	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte[] messageDigest = digest.digest();

			// Create Hex String
			StringBuilder hexString = new StringBuilder();
			for (byte md : messageDigest)
				hexString.append(Integer.toHexString(0xFF & md));
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
		}
		return "";
	}

	public static float poundsToKilograms(float pounds) {
		return (float) (pounds * 0.453592);
	}

	public static float kilogramsToPounds(float kilograms) {
		return (float) (kilograms * 2.20462);
	}

	public static float footToCentimeter(float foot) {
		return (float) (foot * 30.48);
	}

	public static float centimeterToFoot(float centimeters) {
		return (float) (centimeters * 0.0328084);
	}

	public static String readFromRawTextFile(Context c, int id) {
		InputStream inputStream = c.getResources().openRawResource(id);

		InputStreamReader inputReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputReader);
		String line;
		StringBuilder text = new StringBuilder();

		try {
			while ((line = bufferedReader.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
		} catch (IOException e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
			return null;
		}
		return text.toString();
	}

	public static Bitmap drawTextToBitmap(Context gContext, int gResId, String gText) {
		Resources resources = gContext.getResources();
		float scale = resources.getDisplayMetrics().density;
		Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

		Bitmap.Config bitmapConfig = bitmap.getConfig();
		// set default bitmap config if none
		if (bitmapConfig == null) {
			bitmapConfig = Bitmap.Config.ARGB_8888;
		}
		// resource bitmaps are immutable,
		// so we need to convert it to mutable one
		bitmap = bitmap.copy(bitmapConfig, true);

		Canvas canvas = new Canvas(bitmap);
		// new anti-alised Paint
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// text color - #3D3D3D
		paint.setColor(Color.rgb(224, 224, 224));
		// text size in pixels
		paint.setTextSize((int) (14 * scale));
		// text shadow
		paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

		// draw text to the Canvas center
		Rect bounds = new Rect();
		paint.getTextBounds(gText, 0, gText.length(), bounds);
		int x = (bitmap.getWidth() - bounds.width()) / 2;
		int y = (bitmap.getHeight() + bounds.height()) / 2;

		canvas.drawText(gText, x, y, paint);

		return bitmap;
	}

	/**
	 * @return the last know best location
	 */
	public static Location getLastBestLocation(Context c, LocationManager locManager) {
		if (checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION)
				== PackageManager.PERMISSION_GRANTED && checkSelfPermission(c,
				Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

			Location locationGPS = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			Location locationNet = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			long gpsLocationTime = 0;
			if (null != locationGPS) {
				gpsLocationTime = locationGPS.getTime();
			}

			long netLocationTime = 0;

			if (null != locationNet) {
				netLocationTime = locationNet.getTime();
			}
			if (0 < gpsLocationTime - netLocationTime) {
				return locationGPS;
			} else return locationNet;
		}
		return null;
	}

	public static void moveFile(String inputPath, String inputFile, String outputPath) {

		InputStream in;
		OutputStream out;
		try {

			//create output directory if it doesn't exist
			File dir = new File(outputPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			in = new FileInputStream(inputPath + inputFile);
			out = new FileOutputStream(outputPath + inputFile);

			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}

			in.close();

			// write the output file
			out.flush();
			out.close();

			// delete the original file
			boolean isSuccess = new File(inputPath + inputFile).delete();
			Log.e(AppConstants.TAG, "Delete Success: " + isSuccess);
		} catch (Exception e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
		}
	}

	public static float bytesToMB(long bytes) {
		return bytes / 1048576f;
	}

	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}

	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}

	/**
	 * Reads the given {@link InputStream File Stream} and produces string from file content
	 *
	 * @param stream The provided File Stream
	 * @return String representation of file contents
	 */
	public static String readFromFile(InputStream stream) {
		//Read text from file
		StringBuilder text = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			String line;

			while ((line = br.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
			br.close();
		} catch (IOException e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
		}
		return text.toString();
	}

	/**
	 * Writes the given text to a temporary file and returns it
	 *
	 * @param text The text to be written to the file
	 * @return file
	 */
	public static File writeToFile(String text) {
		File f = new File("temp.txt");
		if (!f.exists()) {
			try {
				boolean isSuccess = f.createNewFile();
				Log.e(AppConstants.TAG, "File Creation Success: " + isSuccess);
			} catch (IOException e) {
				Log.e(AppConstants.TAG, e.getMessage(), e);
			}
		}
		try {
			//BufferedWriter for performance, true to set append to file flag
			BufferedWriter buf = new BufferedWriter(new FileWriter(f, true));
			buf.append(Calendar.getInstance().getTime().toString()).append(" -> ").append(text);
			buf.newLine();
			buf.newLine();
			buf.close();
		} catch (IOException e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
		}
		return f;
	}

	public static boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			Log.e(AppConstants.TAG, ex.getMessage(), ex);
			try {
				new JSONArray(test);
			} catch (JSONException ex1) {
				Log.e(AppConstants.TAG, ex1.getMessage(), ex1);
				return false;
			}
		}
		return true;
	}

	public static void showLightSnackBar(LinearLayout layout, String message) {
		Snackbar s = Snackbar.make(layout, message, Snackbar.LENGTH_SHORT);
		TextView v = (TextView) s.getView().findViewById(android.support.design.R.id.snackbar_text);
		v.setTextColor(Color.WHITE);
		s.show();
	}

	/**
	 * get datetime
	 */
	public static String getDate() {
		String pattern = "DD yyyy-MM-dd";
		return getDateFromPattern(pattern);
	}

	public static void appendLog(String text) {
		File logFile = new File("sdcard/" + AppConstants.LOG_FILE_NAME);
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				Log.e(AppConstants.TAG, e.getMessage(), e);
			}
		}
		try {
			//BufferedWriter for performance, true to set append to file flag
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
			buf.append(Calendar.getInstance().getTime().toString()).append(" -> ").append(text);
			buf.newLine();
			buf.newLine();
			buf.close();
		} catch (IOException e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
		}
	}

	public static Drawable viewToDrawable(Context c, View v) {
		v.setDrawingCacheEnabled(true);

		// this is the important code :)
		// Without it the view will have a dimension of 0,0 and the bitmap will be null
		v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

		v.buildDrawingCache(true);
		Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
		v.setDrawingCacheEnabled(false); // clear drawing cache
		return new BitmapDrawable(c.getResources(), b);
	}

	public static void displayShareDialog(Activity a, String message) {
		//Share the text
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, message);
		sendIntent.setType("text/plain");
		a.startActivity(sendIntent);
	}

	//Show a custom alert dialog
	public static void showAlertDialog(Context c, Activity activity, String title, String message) {
		new AlertDialog.Builder(c)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(android.R.string.ok, (dialog, which) -> {
					Activity a = (Activity) c;
					a.startActivity(new Intent(c, activity.getClass()));
					a.finish();
				}).show();
	}

	/**
	 * @param bitmap the bitmap to be converted
	 * @return converting bitmap and return a string
	 */
	public static String bitMapToString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.URL_SAFE | Base64.NO_WRAP);
	}

	/**
	 * @param encodedString The string to be decoded
	 * @return bitmap (from given string)
	 */
	public static Bitmap stringToBitMap(String encodedString) {
		try {
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
		} catch (Exception e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
			return null;
		}
	}

	public static void hideKeyboard(Context c) {
		InputMethodManager inputMethodManager = (InputMethodManager)
				c.getSystemService(Context.INPUT_METHOD_SERVICE);
		Activity a = (Activity) c;
		View view = a.getCurrentFocus();
		if (inputMethodManager != null && view != null) {
			inputMethodManager.hideSoftInputFromWindow(
					view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public static String getMonthForInt(int num) {
		String month = "wrong";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (num >= 0 && num <= 11) {
			month = months[num];
		}
		return month;
	}

	public static Date parseDateAndTime(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
		Date date = null;
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
		}
		return date;
	}

	public static Date parseDateOnly(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		Date date = null;
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
		}
		return date;
	}

	public static String dateSlashToDash(String dateString) {
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		Date date = null;
		try {
			date = format1.parse(dateString);
		} catch (ParseException e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
		}
		return format2.format(date);
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	public static void showProgress(View progressbar, final boolean show) {
		// The ViewPropertyAnimator APIs are not available, so simply show
		// and hide the relevant UI components.
		if (progressbar != null) {
			progressbar.setVisibility(show ? View.VISIBLE : View.GONE);
		}
	}

	public static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (String c : children) {
				boolean success = deleteDir(new File(dir, c));
				if (!success) {
					return false;
				}
			}
			return dir.delete();
		} else {
			return dir != null && dir.isFile() && dir.delete();
		}
	}

	public static void hideViews(View... views) {
		for (View v : views)
			if (v != null)
				v.setVisibility(View.GONE);
	}

	public static void showViews(View... views) {
		for (View v : views)
			if (v != null)
				v.setVisibility(View.VISIBLE);
	}

	public static byte[] objectToStream(Object o) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(bos);
			os.writeObject(o);
			os.close();
		} catch (IOException e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
		}
		return bos.toByteArray();
	}

	/**
	 * Read's the given {@link InputStream File Stream} and produces string from file content
	 *
	 * @param stream The provided File Stream
	 * @return String representation of file contents
	 */
	public static String readFile(InputStream stream) {
		//Read text from file
		StringBuilder text = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			String line;

			while ((line = br.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
			br.close();
		} catch (IOException e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
		}
		return text.toString();
	}

	/**
	 * Return sha512 encrypted representation of given String
	 */
	public static String SHA512(String passwordToHash) {
		String encryptedString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(passwordToHash.getBytes());
			byte[] bytes = md.digest();
			String out = "";
			for (byte temp : bytes) {
				String s = Integer.toHexString(temp);
				while (s.length() < 2) {
					s = "0" + s;
				}
				s = s.substring(s.length() - 2);
				out += s;
			}
			encryptedString = out;
		} catch (NoSuchAlgorithmException e) {
			Log.e(AppConstants.TAG, e.getMessage(), e);
		}
		return encryptedString;
	}

	public static boolean hasPermissions(Context context, String... permissions) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
				&& context != null && permissions != null) {
			for (String permission : permissions) {
				if (checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
					appendLog("Permission Not Granted: " + permission, AppConstants.LOG_FILE_NAME);
					return false;
				}
			}
		}
		return true;
	}

	/*public static boolean isGooglePlayServicesAvailable(Activity activity) {
		GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
		int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
		if(status != ConnectionResult.SUCCESS) {
			if(googleApiAvailability.isUserResolvableError(status)) {
				googleApiAvailability.getErrorDialog(activity, status, 2404).show();
			}
			return false;
		}
		return true;
	}*/

	public static void buildAlertMessageNoGps(final Context c) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(c);
		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
				.setCancelable(false)
				.setPositiveButton("Yes", (dialog, id) -> {
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					c.startActivity(intent);
				})
				.setNegativeButton("No", (dialog, id) -> dialog.cancel());
		final AlertDialog alert = builder.create();
		alert.show();
	}

	public static void hideSoftKeyboard(Activity activity, View view) {
		InputMethodManager imm = (InputMethodManager)
				activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
	}

	public static void disableEditText(EditText editText) {
		editText.setFocusable(false);
		editText.setFocusableInTouchMode(false);
		editText.setClickable(false);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public static Drawable getRoundIconView(Context c, int imageID, int tintColor) {
		FloatingActionButton icon = new FloatingActionButton(c);
		icon.setImageTintList(ColorStateList.valueOf(tintColor));
		Drawable d = ContextCompat.getDrawable(c, imageID);
		d.setTint(tintColor);
		icon.setImageDrawable(d);

		return viewToDrawable(c, icon);
	}

    /*
	public static void showAppHashKey(String packageName) {
        Cricketer app = new Cricketer();
        try {
            PackageInfo info = app.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign= Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e(Cricketer.TAG, "MY KEY HASH: " + sign);
                Toast.makeText(app.getApplicationContext(), sign, Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }*/
}