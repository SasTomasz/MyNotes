package com.example.android.mynotes.Utility;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerDecoration extends RecyclerView.ItemDecoration {
    private int mItemSpace;

    public RecyclerDecoration(int itemSpace) {
        this.mItemSpace = itemSpace;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull
            RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = mItemSpace;
    }
}
