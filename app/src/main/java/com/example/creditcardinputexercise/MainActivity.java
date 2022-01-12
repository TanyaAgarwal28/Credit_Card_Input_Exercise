package com.example.creditcardinputexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    EditText cardNumber,date,securityCode,firstName,lastName;
    Button submit;
    AlertDialog.Builder dialog;
    long num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardNumber=findViewById(R.id.editText1);
        date=findViewById(R.id.editText2);
        securityCode=findViewById(R.id.editText3);
        firstName=findViewById(R.id.editText4);
        lastName=findViewById(R.id.editText5);
        submit=findViewById(R.id.button);
        dialog = new AlertDialog.Builder(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String card=cardNumber.getText().toString().trim();
                String d=date.getText().toString().trim();
                String sc=securityCode.getText().toString().trim();
                String fn=firstName.getText().toString().trim();
                String ln=lastName.getText().toString().trim();
                if(card.length()!=0) {
                    num = Long.parseLong(card);
                }
                if(card.isEmpty() || !isValid(num)) {
                    cardNumber.setError("Please enter valid card number");
                }
                if(fn.isEmpty() || !fn.matches("^[a-zA-Z]*$")){
                    firstName.setError("Please enter valid first name");
                }
                if(ln.isEmpty() || !ln.matches("^[a-zA-Z]*$")){
                    lastName.setError("Please enter valid last name");
                }
                if(sc.isEmpty() || !sc.matches("^[0-9]{3,4}$")){
                    securityCode.setError("Please enter valid security code");
                }
                if(d.isEmpty() || !d.matches("^[0-9][0-9][\\/][0-9][0-9]$")){
                    date.setError("Please enter valid date");
                }
                if(isValid(num) && fn.matches("^[a-zA-Z]*$") && ln.matches("^[a-zA-Z]*$") && sc.matches("^[0-9]{3,4}$") && d.matches("^[0-9][0-9][\\/][0-9][0-9]$")) {
                    dialog.setMessage("Payment Successfull");
                    dialog.setPositiveButton("OK",
                            (dialog, which) -> {
                                Intent i=new Intent(MainActivity.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }

            }
        });



    }
    public static boolean isValid(long number)
    {
        return (getSize(number) >= 13 &&
                getSize(number) <= 16) &&
                (prefixMatched(number, 4) ||
                        prefixMatched(number, 5) ||
                        prefixMatched(number, 37) ||
                        prefixMatched(number, 6)) &&
                ((sumOfDoubleEvenPlace(number) +
                        sumOfOddPlace(number)) % 10 == 0);
    }
    public static int sumOfDoubleEvenPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 2; i >= 0; i -= 2)
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);

        return sum;
    }

    public static int getDigit(int number)
    {
        if (number < 9)
            return number;
        return number / 10 + number % 10;
    }
    public static int sumOfOddPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 1; i >= 0; i -= 2)
            sum += Integer.parseInt(num.charAt(i) + "");
        return sum;
    }
    public static boolean prefixMatched(long number, int d)
    {
        return getPrefix(number, getSize(d)) == d;
    }

    public static int getSize(long d)
    {
        String num = d + "";
        return num.length();
    }

    public static long getPrefix(long number, int k)
    {
        if (getSize(number) > k) {
            String num = number + "";
            return Long.parseLong(num.substring(0, k));
        }
        return number;
    }
}