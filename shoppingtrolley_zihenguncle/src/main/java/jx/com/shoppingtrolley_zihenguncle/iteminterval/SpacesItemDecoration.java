package jx.com.shoppingtrolley_zihenguncle.iteminterval;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author 郭淄恒
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        if (space==11){
            outRect.top = space;
        }else {
            outRect.left = space;
            outRect.bottom = space;
            outRect.top = space;
        }
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0){
            outRect.top = space;
        }
    }
}
