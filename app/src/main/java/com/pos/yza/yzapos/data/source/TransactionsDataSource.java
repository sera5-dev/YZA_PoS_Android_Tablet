package com.pos.yza.yzapos.data.source;

import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.pos.yza.yzapos.data.representations.Branch;
import com.pos.yza.yzapos.data.representations.Transaction;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Dalzy Mendoza on 12/1/18.
 */

public interface TransactionsDataSource {

    interface LoadTransactionsCallback {
        void onTransactionsLoaded(List<Transaction> transactions);
        void onDataNotAvailable();
    }

    interface GetTransactionCallback {
        void onTransactionLoaded(Transaction transaction);
        void onDataNotAvailable();
    }

    void getTransactions(@NonNull LoadTransactionsCallback callback);

    void getTransactionById(@NonNull String transactionId, @NonNull GetTransactionCallback callback);

    // Functions for Generating Report
    // void getTransactionsByBranch(Branch branch, @NonNull LoadTransactionsCallback callback);

    void saveTransaction(@NonNull Transaction transaction);
    void saveTransaction(@NonNull Transaction transaction, Response.Listener<JSONObject> responseListener);

    void refreshTransactions();

    void deleteOldTransactions();

    void deleteAllTransactionsByBranch();

    void cancelTransaction(@NonNull String transactionId);

    void refundTransaction(@NonNull String transactionId);

    void sendReport(@NonNull Branch branch, @NonNull int year, @NonNull int month, @NonNull int day);

}


