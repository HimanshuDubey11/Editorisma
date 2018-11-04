package project.horcrux.com.editorisma.utilities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import project.horcrux.com.editorisma.R;
import project.horcrux.com.editorisma.activity.HomeActivity;
import project.horcrux.com.editorisma.activity.MyImageEditor;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.Images> {


    ArrayList<String> list;
    Context context;

    public TemplateAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TemplateAdapter.Images onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_template_adapter,null);

        return new Images(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateAdapter.Images images, int i) {

        byte[] decodedString = Base64.decode(list.get(i), Base64.DEFAULT);
        final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        images.imageView.setImageBitmap(decodedByte);

        images.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeActivity.myBitmap = decodedByte;
                context.startActivity(new Intent(context, MyImageEditor.class));

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Images extends RecyclerView.ViewHolder {
        ImageView imageView;
        public Images(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.templateadapterimageview);

        }
    }
}
