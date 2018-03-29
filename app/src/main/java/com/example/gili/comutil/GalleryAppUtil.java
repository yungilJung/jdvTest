package com.example.gili.comutil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * Created by gili on 2018-03-12.
 */

public class GalleryAppUtil {

    public  static void sendIntentSingleImage(Activity context, int request){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent, request);
    }

    public  static void sendIntentMultipleImage(Activity context, int request){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(intent, request);
    }

    public  static String getSingleFilePath(Context context, Intent data){
        String [] projection = {MediaStore.Images.Media.DATA};
        Cursor curosr = context.getContentResolver().query(data.getData(), projection, null, null, MediaStore.Images.Media.DATE_MODIFIED+" desc");
        curosr.moveToFirst();
        return curosr.getString(0);
    }

    public static String getFilePathFromDocumentUri(Context context, Uri uri){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            String docId = DocumentsContract.getDocumentId(uri);
            String [] split = docId.split(":");
            String type = split[0];
            Uri contentUri = null;
            if("image".equals(type)){
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            }
            String selection = MediaStore.Images.Media._ID + "=?";
            String [] selectionArgs = new String [] {split[1]};
            String column = "_data";
            String [] projection = {column};
            Cursor cursor = context.getContentResolver().query(contentUri, projection, selection, selectionArgs, null);
            String filePath = null;
            if(cursor != null && cursor.moveToFirst()){
                int column_index = cursor.getColumnIndexOrThrow(column);
                filePath = cursor.getString(column_index);
            }
            cursor.close();;
            return filePath;
        }else
        {
            return  null;
        }
    }

    public static String getFilePathFromUriSegment(Context context, Uri uri){
        String selection = MediaStore.Images.Media._ID+"=?";
        String[] selectionArgs = new String[]{uri.getLastPathSegment()};
        String column = "_data";
        String [] projection = {column};
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null);
        String filePath = null;
        if(cursor != null && cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(column);
            filePath = cursor.getString(column_index);
        }
        cursor.close();;
        return filePath;
    }

}
