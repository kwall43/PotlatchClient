package org.magnum.imageup.client;

/**
 * Created by Kendra on 11/29/2014.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.view.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import retrofit.RetrofitError;
import retrofit.mime.TypedFile;

import org.magnum.imageup.client.Gift;
import org.magnum.imageup.client.GiftSvcApi;

public class AddGiftActivity extends Activity{

    private ImageView imgView;
    public static final String PHOTO_PATH = "/gift";
    //public static final String PHOTO_BITMAP = "/photo_bitmap";
    //private Bitmap pImageBitmap = null;
    //private Uri pTempImageFile = null;

    private ProgressBar mProgressBar;
    private EditText mTitleTextView;
    private EditText mMessageTextView;
    //private View mLoadingView;
    //private TextView mSendingTextView;

    //private SendingTask mSendingTask = null;

    public static Intent createGiftIntent(Uri imageUri, Context context){
        Intent intent = new Intent(context, AddGiftActivity.class);
        intent.putExtra(AddGiftActivity.PHOTO_PATH, imageUri);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gift);

        imgView = (ImageView) findViewById(R.id.image_view);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        //mLoadingView = findViewById(R.id.loading_view);
        //mSendingTextView = (TextView) findViewById(R.id.sending_text_view);

        //showImageLoadingProgress(false);

        /**Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null)
        {
            if (extras.containsKey(PHOTO_BITMAP))
            {
                setImage((Bitmap) extras.get(PHOTO_BITMAP));
            }
        //    else if (extras.containsKey(PHOTO_PATH))
        //    {
        //        startLoadImage((Uri) extras.get(PHOTO_PATH));
        //    }
        }*/

        mTitleTextView = (EditText) findViewById(R.id.title_edit_text);
        mTitleTextView.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                mTitleTextView.setError(null);
            }
        });

        mMessageTextView = (EditText) findViewById(R.id.message_text_view);
        mMessageTextView.setOnEditorActionListener(new EditText.OnEditorActionListener()
        {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_SEND)
                {
                    sendAction(v);
                    return true;
                }
                return false;
            }
        });
    }

    /**private void setImage(Bitmap bitmap)
    {
        pImageBitmap = bitmap;
        if (imgView != null)
        {
            imgView.setImageBitmap(pImageBitmap);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        imgView.setImageBitmap(pImageBitmap);
        //mLoadingView.setVisibility((mSendingTask != null) ? View.VISIBLE : View.GONE);
    }*/


    /**@Override
 *public boolean onCreateOptionsMenu(Menu menu) {
 * // Inflate the menu; this adds options to the action bar if it is present.
 * getMenuInflater().inflate(R.menu.main, menu);
 * return true;
 * }
*/


private void selectImage() {

    final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

    AlertDialog.Builder builder = new AlertDialog.Builder(AddGiftActivity.this);
    builder.setTitle("Add Photo!");
    builder.setItems(options, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int item) {
            if (options[item].equals("Take Photo"))
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, 1);

            }
            else if (options[item].equals("Choose from Gallery"))
            {
                Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);

            }
            else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        }
    });
    builder.show();
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    imgView.setImageBitmap(bitmap);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........", picturePath+"");
                imgView.setImageBitmap(thumbnail);
            }
        }

    }

    public void sendAction(View sender)
    {
        if (mTitleTextView.getText().length() == 0)
        {
            mTitleTextView.setError(getString(R.string.enter_title));
            mTitleTextView.requestFocus();
        }

    }

}



