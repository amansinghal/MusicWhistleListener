package com.music.aman.musicg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.music.aman.musicg.Models.APIInterface;
import com.music.aman.musicg.Models.APIModel;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by AmaN on 9/27/2015.
 */
public class Activity_Paypal extends Activity {
// private static final String TAG = "paymentdemoblog";
    /**
     * - Set to PaymentActivity.ENVIRONMENT_PRODUCTION to move real money.
     * <p/>
     * - Set to PaymentActivity.ENVIRONMENT_SANDBOX to use your test credentials
     * from https://developer.paypal.com
     * <p/>
     * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
     * without communicating to PayPal's servers.
     */
// private static final String CONFIG_ENVIRONMENT =
// PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    // note that these credentials will differ between live & sandbox
// environments.
    private static final String CONFIG_CLIENT_ID = "ARMUy3c9-LrgTH474_uDvxJqbbjTgUP7TcVj0Ykz3u83GO0vDwA6TFKaoMjxvjK7wPu_fCyfqV3ID2CH";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    public static final String PAYMENT_FOR_KEY = "PAYMENT_FOR_KEY";
    public static final String VIEW_FOR_KEY = "VIEW_FOR_KEY";
    public static final String AD_ID_KEY = "AD_ID_KEY";
    private static PayPalConfiguration config = new PayPalConfiguration().environment(CONFIG_ENVIRONMENT).clientId(CONFIG_CLIENT_ID)
// the following are only used in PayPalFuturePaymentActivity.
            .merchantName("Hipster Store")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.example.com/legal"));
    PayPalPayment thingToBuy;
    private RadioGroup radioGroup;
    private Button continueBtn;
    private SharedPreferences preferences;
    private ProgressDialog progressDialog;
    private String payFor, ad_id = "";
    private int viewFor, totCount = 0;

    enum paymentFor {
        ADDVERTISMENT, FACILITY
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(MainActivity.URI_KEY, MODE_PRIVATE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        payFor = getIntent().getStringExtra(PAYMENT_FOR_KEY);
        viewFor = getIntent().getIntExtra(VIEW_FOR_KEY, 0);
        ad_id = getIntent().getStringExtra(AD_ID_KEY);
        setContentView(R.layout.activity_paypal);
        if (ad_id != null) {
            ((TextView) findViewById(R.id.add_header_msg)).setText(getString(R.string.ad_sub_msg));
        }else{
            ad_id = "";
        }
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            final float scale = this.getResources().getDisplayMetrics().density;
            ((RadioButton) radioGroup.getChildAt(0)).setPadding(((RadioButton) radioGroup.getChildAt(0)).getPaddingLeft() + (int) (10.0f * scale + 0.5f),
                    ((RadioButton) radioGroup.getChildAt(0)).getPaddingTop(),
                    ((RadioButton) radioGroup.getChildAt(0)).getPaddingRight(),
                    ((RadioButton) radioGroup.getChildAt(0)).getPaddingBottom());

            ((RadioButton) radioGroup.getChildAt(1)).setPadding(((RadioButton) radioGroup.getChildAt(1)).getPaddingLeft() + (int) (10.0f * scale + 0.5f),
                    ((RadioButton) radioGroup.getChildAt(1)).getPaddingTop(),
                    ((RadioButton) radioGroup.getChildAt(1)).getPaddingRight(),
                    ((RadioButton) radioGroup.getChildAt(1)).getPaddingBottom());
        }
        continueBtn = (Button) findViewById(R.id.pay_continue);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.radio1) {
                    thingToBuy = new PayPalPayment(ad_id.isEmpty() ? new BigDecimal("15") : new BigDecimal("600"), "GBP", payFor, PayPalPayment.PAYMENT_INTENT_SALE);
                    Intent intent1 = new Intent(Activity_Paypal.this,
                            PaymentActivity.class);
                    intent1.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
                    startActivityForResult(intent1, REQUEST_CODE_PAYMENT);
                } else {

                }
            }
        });


    }

    public static Intent getIntent(Context context, int viewFor, String paymentFor) {

        Intent intent = new Intent(context, Activity_Paypal.class);
        intent.putExtra(PAYMENT_FOR_KEY, paymentFor);
        intent.putExtra(VIEW_FOR_KEY, viewFor);
        return intent;
    }

    public static Intent getIntent(Context context, int viewFor, String paymentFor, String ad_id) {

        Intent intent = new Intent(context, Activity_Paypal.class);
        intent.putExtra(PAYMENT_FOR_KEY, paymentFor);
        intent.putExtra(VIEW_FOR_KEY, viewFor);
        intent.putExtra(AD_ID_KEY, ad_id);
        return intent;
    }

    public void onFuturePaymentPressed(View pressed) {
        Intent intent = new Intent(Activity_Paypal.this,
                PayPalFuturePaymentActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject().toString(4));
                        JSONObject jsonObject = confirm.toJSONObject();
                        jsonObject = jsonObject.getJSONObject("response");
                        if (jsonObject.getString("state").equalsIgnoreCase("approved")) {
                            if (payFor.equalsIgnoreCase("Ad Subscription")) {
                                updateAdSubcription();
                            } else {
                                updateFacilitySubcription();
                            }
                        } else {
                            Toast.makeText(Activity_Paypal.this, "Payment not accepted by Paypal.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");

            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(Activity_Paypal.this, "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.", Toast.LENGTH_LONG).show();
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {

            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject()
                                .toString(4));
                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);
                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(),
                                "Future Payment code received from PayPal",
                                Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample",
                                "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }

    public void onFuturePaymentPurchasePressed(View pressed) {
// Get the Application Correlation ID from the SDK
        String correlationId = PayPalConfiguration
                .getApplicationCorrelationId(this);
        Log.i("FuturePaymentExample", "Application Correlation ID: "
                + correlationId);
// TODO: Send correlationId and transaction details to your server for
// processing with
// PayPal...
        Toast.makeText(getApplicationContext(),
                "App Correlation ID received from SDK", Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onDestroy() {
// Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void updateFacilitySubcription() {
        progressDialog.show();

        final APIInterface apiInterface = Utils.getAdapterWebService();

        apiInterface.getUSerSubscriptionUpdate("updatefacilitySubcription", preferences.getString(MainActivity.USER_ID_KEY, ""), "15", "Paypal", new Callback<APIModel>() {
            @Override
            public void success(APIModel apiModel, Response response) {
                System.out.println(apiModel);
                progressDialog.dismiss();
                if (apiModel.getSuccess().equals("1")) {
                    Toast.makeText(Activity_Paypal.this, "Subscribed.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra(VIEW_FOR_KEY, viewFor);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(Activity_Paypal.this, "Error while subscribing please try later", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                if (totCount < 2) {
                    updateFacilitySubcription();
                    totCount++;
                }
                Toast.makeText(Activity_Paypal.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateAdSubcription() {

        progressDialog.show();

        final APIInterface apiInterface = Utils.getAdapterWebService();

        apiInterface.addSubscriptionUpdate("addSubcription", preferences.getString(MainActivity.USER_ID_KEY, ""), "600", "Paypal", ad_id, "6", new Callback<APIModel>() {
            @Override
            public void success(APIModel apiModel, Response response) {
                System.out.println(apiModel);
                progressDialog.dismiss();
                if (apiModel.getSuccess().equals("1")) {
                    Toast.makeText(Activity_Paypal.this, "Subscribed.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra(VIEW_FOR_KEY, viewFor);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(Activity_Paypal.this, "Error while subscribing please try later", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                if (totCount < 2) {
                    updateAdSubcription();
                    totCount++;
                }
                Toast.makeText(Activity_Paypal.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
