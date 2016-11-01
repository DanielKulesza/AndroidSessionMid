package scs2682.exercise06.data;

/**
 * Created by Jurgis on 2016-10-25.
 */
import android.support.annotation.NonNull;

public class Contact {
    @NonNull
    public final String firstName;
    @NonNull
    public final String lastName;
    @NonNull
    public final String phone;
    @NonNull
    public final String email;

    public Contact(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName != null ? firstName : "";
        this.lastName = lastName != null ? lastName : "";
        this.phone = phone != null  ? phone : "";
        this.email = email != null ? email  : "";


    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Contact)){
            return false;
        }
        Contact other = (Contact) obj;
        return firstName.equals(other.firstName) && email.equals(other.email);
    }

    @Override
    public int hashCode() {
        return firstName.hashCode() ^ email.hashCode();
    }
}
