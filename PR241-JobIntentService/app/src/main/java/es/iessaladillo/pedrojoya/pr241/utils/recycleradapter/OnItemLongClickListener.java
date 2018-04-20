package es.iessaladillo.pedrojoya.pr241.utils.recycleradapter;

import android.view.View;

public interface OnItemLongClickListener<M> {
    @SuppressWarnings({"SameReturnValue", "unused"})
    boolean onItemLongClick(View view, M item, int position, long id);
}
