package com.food.foodapp.adapter;

import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;

import com.food.foodapp.models.Order;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MyPrintDocumentAdapter extends PrintDocumentAdapter {
    private Order order;

    public MyPrintDocumentAdapter(Order order) {
        this.order = order;
    }

    @Override
    public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes1, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
        if (cancellationSignal.isCanceled()) {
            layoutResultCallback.onLayoutCancelled();
            return;
        }

        PrintDocumentInfo.Builder builder = new PrintDocumentInfo.Builder("print_output.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(1);

        PrintDocumentInfo info = builder.build();
        layoutResultCallback.onLayoutFinished(info, !printAttributes.equals(printAttributes1));
    }

    @Override
    public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
        InputStream input = null;
        OutputStream output = null;

        try {
            // Assuming you have a valid InputStream for your content
            input = getOrderContentAsStream(order); // Implement getOrderContentAsStream method
            output = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
        } catch (Exception e) {
            writeResultCallback.onWriteFailed(e.getMessage());
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private InputStream getOrderContentAsStream(Order order) {
        String orderContent = "Order ID: " + order.getId() + "\n"
                + "Customer: " + order.getUsername() + "\n"
                + "Total: " + order.getTotal() + "\n";

        return new ByteArrayInputStream(orderContent.getBytes(StandardCharsets.UTF_8));
    }
}
