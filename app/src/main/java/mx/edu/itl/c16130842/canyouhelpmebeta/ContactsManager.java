package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactsManager {

    private final int CONTACT_PICKER_RESULT = 898;
    private Activity activity;
    public ContactsManager(Activity activity){
        this.activity = activity;
    }

    public void selectContact(){
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        this.activity.startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }


    /**
     * Llamada en el ActivityResult de nuestra activity para obtener el email del usuario
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data, onSelectedPhone onSelectedPhoneHandler){
        if(requestCode == CONTACT_PICKER_RESULT){
            if (resultCode == Activity.RESULT_OK) {

                Cursor cursor = null;
                String phone = "";
                try {
                    Uri result = data.getData();

                    String id = result.getLastPathSegment();

                    cursor = this.activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[] { id }, null);
                    int phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);

                    if (cursor.moveToFirst())
                        phone = cursor.getString(phoneIdx);

                } catch (Exception e) {
                } finally {
                    if (cursor != null)
                        cursor.close();
                    if (phone.length() == 0)
                        onSelectedPhoneHandler.onFailure();
                    else
                        onSelectedPhoneHandler.onSuccess(phone);
                }

            } else {
                onSelectedPhoneHandler.onFailure();
            }
        }
    }

    public interface onSelectedPhone{ void onSuccess(String phone); void onFailure();};

}
