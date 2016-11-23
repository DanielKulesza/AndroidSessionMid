package scs2682.exercise06;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import scs2682.exercise06.data.Contact;
import scs2682.exercise06.ui.Form;

public class AppActivity extends AppCompatActivity {
    private final class SpinnerAdapter extends ArrayAdapter<Contact> {
        private final LayoutInflater layoutInflater;

        private int selectedItemPosition;


        private  SpinnerAdapter(@NonNull Context context, @NonNull List<Contact> items){
            super(context, 0, items);

            layoutInflater = LayoutInflater.from(context);

        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.appactivity_cell, parent, false);

            }
            Contact contact = getItem(position);
            if(contact  != null){
                String title  = contact.firstName + " " +  contact.lastName;
                ((TextView) convertView).setText(title);
            }
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.appactivity_dropdown_cell, parent, false);
            }
            Contact  contact = getItem(position);
            if(contact != null){
                String title = contact.firstName + " " + contact.lastName;
                ((TextView) convertView).setText(title);
            }

            int highlightBackgroundColor = selectedItemPosition == position ? Color.LTGRAY : 0;
            convertView.setBackgroundColor(highlightBackgroundColor);
            return convertView;
        }
    }
    private Button add;
    private TextView edit;
    private Spinner spinner;
    private SpinnerAdapter spinnerAdapter;

    @NonNull
    private final List<Contact> contacts = new ArrayList<>();

    public void onContactUpdated(@NonNull Form form, @Nullable Contact contact){
        if (contact != null && !TextUtils.isEmpty(contact.firstName) &&  !TextUtils.isEmpty(contact.email)){
            //all good time  to update spinner
            int position = contacts.indexOf(contact);

            if(position > -1){
                contacts.set(position, contact);
            }
            else{
                contacts.add(contact);
                position = contacts.size()-1;

            }
            // set the selection of the spinner right away
            spinnerAdapter.notifyDataSetChanged();
            spinner.setSelection(position, false);
        }

        //always remove the fragment instance
        getFragmentManager().beginTransaction().remove(form).commit();
        getFragmentManager().popBackStack();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appactivity);

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment form = getFragmentManager().findFragmentByTag(Form.NAME);
                if(form == null){
                    //no fragment opened
                    getFragmentManager().beginTransaction()
                            .add(R.id.appactivity, new Form(), Form.NAME)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        edit = (TextView) findViewById(R.id.edit);
        spinnerAdapter = new SpinnerAdapter(this,contacts);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = spinnerAdapter.getItem(position);

                spinnerAdapter.selectedItemPosition = spinner.getSelectedItemPosition();
                showContacts(contact);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void showContacts(Contact contact){
        Fragment form = getFragmentManager().findFragmentByTag(Form.NAME);
        if(form != null){
            return;
        }
        if(form == null){
            //no fragment opened
            form = Form.newInstance(contact.firstName,contact.lastName, contact.phone, contact.email);
            getFragmentManager().beginTransaction()
                    .add(R.id.appactivity, form, Form.NAME)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
