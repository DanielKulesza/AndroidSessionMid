package scs2682.exercise06.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import scs2682.exercise06.AppActivity;
import scs2682.exercise06.R;
import scs2682.exercise06.data.Contact;

public class Form extends Fragment {
    public static final String NAME = Form.class.getSimpleName();



    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_LAST_NAME = "lastName";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";

    public static Form newInstance(String firstName, String lastName, String phone, String email){
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FIRST_NAME, firstName);
        bundle.putString(KEY_LAST_NAME, lastName);
        bundle.putString(KEY_PHONE, phone);
        bundle.putString(KEY_EMAIL, email);

        Form form = new Form();
        form.setArguments(bundle);
        return form;
    }

    private EditText firstName;
    private EditText lastName;
    private EditText phone;
    private EditText email;

    private Button cancel;
    private Button update;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String firstNameValue = "";
        String lastNameValue = "";
        String  phoneValue  = "";
        String emailValue = "";

        if(savedInstanceState != null &&  savedInstanceState.containsKey(KEY_FIRST_NAME)){
            firstNameValue = savedInstanceState.getString(KEY_FIRST_NAME);
            lastNameValue = savedInstanceState.getString(KEY_LAST_NAME);
            phoneValue = savedInstanceState.getString(KEY_PHONE);
            emailValue = savedInstanceState.getString(KEY_EMAIL);


        }
        else if (getArguments() != null){
            firstNameValue = getArguments().getString(KEY_FIRST_NAME, "");
            lastNameValue = getArguments().getString(KEY_LAST_NAME, "");
            phoneValue = getArguments().getString(KEY_PHONE, "");
            emailValue = getArguments().getString(KEY_EMAIL, "");

        }

        firstName = (EditText)view.findViewById(R.id.first_name);
        firstName.setText(firstNameValue);

        lastName = (EditText)view.findViewById(R.id.last_name);
        lastName.setText(lastNameValue);

        phone = (EditText)view.findViewById(R.id.phone);
        phone.setText(phoneValue);

        email = (EditText)view.findViewById(R.id.email);
        email.setText(emailValue);

        cancel = (Button)view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction()
                        .remove(Form.this)
                        .commit();

                getActivity().getFragmentManager().popBackStack();
            }
        });
        update = (Button)view.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameValue = firstName.getText().toString();
                String emailValue = email.getText().toString();

                if(!TextUtils.isEmpty(firstNameValue) && !TextUtils.isEmpty(emailValue)){
                    //both are vaild, proceed
                    ((AppActivity) getActivity()).onContactUpdated(Form.this, new Contact(firstNameValue, lastName.getText().toString(),
                        phone.getText().toString(), emailValue));
                }
                else{
                    Toast.makeText(getActivity(), getString(R.string.first_name_and_email_cannot_be_empty),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_FIRST_NAME, firstName.getText().toString());
        outState.putString(KEY_LAST_NAME, lastName.getText().toString());
        outState.putString(KEY_PHONE, phone.getText().toString());
        outState.putString(KEY_EMAIL, email.getText().toString());
    }
}
