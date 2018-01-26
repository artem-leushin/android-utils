package com.jetruby.android.utils;



import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class LayoutManagerFactories {

    private LayoutManagerFactories() {
    }

    public static LayoutManagerFactory linear() {
        return recyclerView -> new LinearLayoutManager(recyclerView.getContext());
    }

    public static LayoutManagerFactory linear(@Orientation final int orientation, final boolean reverseLayout) {
        return recyclerView -> new LinearLayoutManager(recyclerView.getContext(), orientation, reverseLayout);
    }

    public static LayoutManagerFactory grid(final int spanCount) {
        return recyclerView -> new GridLayoutManager(recyclerView.getContext(), spanCount);
    }

    public static LayoutManagerFactory grid(final int spanCount, @Orientation final int orientation, final boolean reverseLayout) {
        return recyclerView ->
                new GridLayoutManager(recyclerView.getContext(), spanCount, orientation, reverseLayout);
    }

    public static LayoutManagerFactory staggeredGrid(final int spanCount) {
        return staggeredGrid(spanCount, LinearLayoutManager.VERTICAL);
    }

    public static LayoutManagerFactory staggeredGrid(final int spanCount, @Orientation final int orientation) {
        return staggeredGrid(spanCount, StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS, orientation);
    }

    public static LayoutManagerFactory staggeredGrid(final int spanCount, int gapStrategy, @Orientation final int orientation) {
        return recyclerView -> {
            StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(spanCount, orientation);
            lm.setGapStrategy(gapStrategy);
            return lm;
        };
    }


    public interface LayoutManagerFactory {
        RecyclerView.LayoutManager create(RecyclerView recyclerView);
    }

    @IntDef({LinearLayoutManager.HORIZONTAL, LinearLayoutManager.VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }
}

