package com.pos.yza.yzapos.newtransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.pos.yza.yzapos.Injection;
import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.newtransaction.cart.CartFragment;
import com.pos.yza.yzapos.newtransaction.cart.CartActions;
import com.pos.yza.yzapos.newtransaction.cart.CartPresenter;
import com.pos.yza.yzapos.newtransaction.categoryselection.CategorySelectionFragment;
import com.pos.yza.yzapos.newtransaction.categoryselection.CategorySelectionPresenter;
import com.pos.yza.yzapos.newtransaction.customerdetails.CustomerDetailsFragment;
import com.pos.yza.yzapos.newtransaction.customerdetails.CustomerDetailsPresenter;
import com.pos.yza.yzapos.newtransaction.payment.PaymentFragment;
import com.pos.yza.yzapos.newtransaction.payment.PaymentPresenter;
import com.pos.yza.yzapos.util.ActivityUtils;

public class NewTransactionActivity extends AppCompatActivity
        implements OnFragmentInteractionListener{

    private static final String CART = "CART";
    private static final String CATEGORY_SELECTION = "CATEGORY_SELECTION";
    private static final String PRODUCT_SELECTION = "PRODUCT_SELECTION";
    private static final String CUSTOMER_DETAILS = "CUSTOMER_DETAILS";
    private static final String PAYMENT = "PAYMENT";

    private CartPresenter mCartPresenter;
    private CustomerDetailsPresenter mCustomerDetailsPresenter;
    private PaymentPresenter mPaymentPresenter;
    private CategorySelectionPresenter mCategorySelectionPresenter;

//    private ArrayList<Product> products;
//    private Customer customer;
//    private Payment payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle(R.string.new_transaction);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        CartFragment cartFragment =
                (CartFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (cartFragment == null) {
            // Create the fragment
            cartFragment = CartFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), cartFragment, R.id.contentFrame, CART);
        }

        // Create the presenter
        mCartPresenter = new CartPresenter(cartFragment);

    }

    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    }

    @Override
    public void onFragmentMessage(String TAG, Object data) {
        switch (TAG) {
            case CART:
                handleCartActions(mCartPresenter.getAction());
                break;
            case CATEGORY_SELECTION:
                break;
            case PRODUCT_SELECTION:
                break;
            case PAYMENT:
                break;
            case CUSTOMER_DETAILS:
                handleCustomerDetailsActions();
                break;
            default:
                break;
        }

    }

    private void handleCustomerDetailsActions(){
        PaymentFragment paymentFragment =
                (PaymentFragment) getSupportFragmentManager().findFragmentByTag(PAYMENT);
        if (paymentFragment == null) {
            // Create the fragment
            paymentFragment = PaymentFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), paymentFragment,
                    R.id.contentFrame, PAYMENT);
        }

        // Create the presenter
        mPaymentPresenter = new PaymentPresenter(paymentFragment);
    }

    private void handleCartActions(CartActions action) {
        switch (action) {
            case ADD_PRODUCT:

                CategorySelectionFragment categorySelectionFragment =
                        (CategorySelectionFragment) getSupportFragmentManager().
                                findFragmentByTag(CATEGORY_SELECTION);

                if (categorySelectionFragment == null) {
                    categorySelectionFragment = CategorySelectionFragment.newInstance();
                    ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                            categorySelectionFragment, R.id.contentFrame, CATEGORY_SELECTION);
                }

                mCategorySelectionPresenter =
                        new CategorySelectionPresenter(categorySelectionFragment,
                                Injection.provideCategoriesRepository(this));

                break;
            case GO_TO_CUSTOMER_DETAILS:

                CustomerDetailsFragment customerDetailsFragment =
                        (CustomerDetailsFragment) getSupportFragmentManager().
                                findFragmentByTag(CUSTOMER_DETAILS);

                if (customerDetailsFragment == null) {
                    // Create the fragment
                    customerDetailsFragment = CustomerDetailsFragment.newInstance();
                    ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),
                            customerDetailsFragment, R.id.contentFrame, CUSTOMER_DETAILS);
                }
                // Create the presenter
                mCustomerDetailsPresenter = new CustomerDetailsPresenter(customerDetailsFragment);

                break;
            default:
                break;
        }

    }
}
