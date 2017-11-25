package com.mtadese.coffeeorder;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.coffeeorder;
 *
 */



import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    int price;
    CheckBox creamCheck, chocolateCheck;
    int creamPrice, chocolatePrice;
    Button buttonOrder;
    String result ;
    String resultChoc ;

    EditText mEdit;
    String cus_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //addListenerOnButtonClick();
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {

        //ensure quantity does not go below 1
        if (quantity < 100) {
            quantity = quantity + 1;

        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "You can't order more than a hundred cups of coffee!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            quantity = 100;
        }


        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {

        //ensure quantity does not go below 1
        if (quantity > 1) {
            quantity = quantity -1;

        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "You can't order less than one cup of coffee!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            quantity = 1;
        }

        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice();

        String summary = createOrderSummary();
        //displayMessage(summary);

        //displayPrice(quantity * 5);
        //String priceMessage = "Total: ₦" + price;
        //priceMessage = priceMessage + "\nQuantity: " + quantity + "\nThank you!" ;
        //displayMessage(priceMessage);

        //calculatePrice(quantity, 10);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:otad2mic@yahoo.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_CC, "");
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for " + cus_name );
        intent.putExtra(Intent.EXTRA_TEXT, summary );
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //displaying an outcome if whipped cream checkbox is true or false
    private String whippedCream(){
        boolean checkState;
        creamCheck=(CheckBox)findViewById(R.id.whipped_cream_checkbox);
        result = "\nAdd Cream? ";
        if (creamCheck.isChecked()) {
            result+="True";
            creamPrice= 1;      //price of one unit served
            //totalamount += 100;
        }
        else {
            result+="False";
        }
        return result;
    }


    //displaying an outcome if whipped cream checkbox is true or false
    private String chocolate(){
        boolean checkState;
        chocolateCheck=(CheckBox)findViewById(R.id.chocolate_checkbox);
        resultChoc = "\nAdd Chocolate? ";
        if (chocolateCheck.isChecked()) {
            resultChoc+="True";
            chocolatePrice = 2;     //price of one unit served
            //totalamount += 100;
        }
        else {
            resultChoc+="False";
        }
        return resultChoc;
    }

    // display the content of an EditText with button click
    private String customerName() {
        mEdit = (EditText)findViewById(R.id.customer_name);
        cus_name = mEdit.getText().toString();
        return cus_name;
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     *       returning total price
     */
    private int calculatePrice() {
        //calling price values of whippedCream and chocolate
        String addCreamPrice = whippedCream();
        String addChocPrice = chocolate();

        // base price of coffee
        int unitPrice = 5;

        //add 1 if customer wants whipped cream
        if (creamCheck.isChecked()) {
            unitPrice = unitPrice + 1;
        }

        //add 2 if customer wants chocolate
        if (chocolateCheck.isChecked()) {
            unitPrice = unitPrice + 2;
        }

        return quantity * unitPrice;
    }

    /*
    *  create orderSummary method
    *
    */
    private String createOrderSummary() {

        int price = calculatePrice();
        String addOn = whippedCream();
        String addChoc = chocolate();
        String addName = customerName();

        String summaryMessage = "Name: " + addName;
        summaryMessage = summaryMessage + addOn;
        summaryMessage = summaryMessage + addChoc;
        summaryMessage = summaryMessage + "\nQuantity: " + quantity ;
        summaryMessage = summaryMessage + "\n" ;
        summaryMessage = summaryMessage + "\nTotal Amount: ₦" + price ;
        summaryMessage = summaryMessage + "\n" ;
        //summaryMessage = summaryMessage + "\nThank you!";
        summaryMessage = summaryMessage + "\n" + getString(R.string.thank_you);

        return summaryMessage;

    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int value) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + value);
    }



}

