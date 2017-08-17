package arnold.agura.com.pickme;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private Button shareBtn;
    private ImageView imgBtn;
    private RelativeLayout mainBg;
    private Uri selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shareBtn = (Button)findViewById(R.id.sharebtn);
        imgBtn = (ImageView)findViewById(R.id.pick);
        mainBg = (RelativeLayout) findViewById(R.id.mainbg);


        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 0);
            }
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, selectedImage);
                shareIntent.setType("image/jpeg");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                     selectedImage = imageReturnedIntent.getData();
                    imgBtn.setVisibility(View.GONE);
                    shareBtn.setVisibility(View.VISIBLE);
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                        Drawable yourDrawable = Drawable.createFromStream(inputStream, selectedImage.toString() );
                        mainBg.setBackground(yourDrawable);
                    } catch (FileNotFoundException e) {
                        Drawable yourDrawable = getResources().getDrawable(R.drawable.pick);
                    }
                }

                break;

        }
    }

}
