//package com.fantasygame.ui.pick.stock;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.fantasygame.R;
//import com.fantasygame.base.BaseAdapter;
//import com.fantasygame.base.BaseHolder;
//import com.fantasygame.data.model.response.SportResponse.Stock;
//
//import java.util.List;
//
//import butterknife.Bind;
//
///**
// * Created by HP on 20/06/2017.
// */
//
//public class ListStockAdapter extends BaseAdapter<Stock, BaseHolder> {
//
//    List<Stock> stocks;
//    SelectStock listener;
//
//    public interface SelectStock {
//        void selectedStock(Stock stock);
//    }
//
//    public ListStockAdapter(LayoutInflater inflater, List<Stock> stocks, SelectStock listener) {
//        super(inflater);
//        this.stocks = stocks;
//        this.listener = listener;
//    }
//
//    @Override
//    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        int layoutId = R.layout.item_stock;
//        return new HowToPlayHolder(inflater.inflate(layoutId, parent, false));
//    }
//
//    public class HowToPlayHolder extends BaseHolder<Stock> {
//        @Bind(R.id.tv_name_stock)
//        TextView tv_name_stock;
//        @Bind(R.id.tv_price_stock)
//        TextView tv_price_stock;
//
//        protected Stock stock;
//
//        public HowToPlayHolder(View itemView) {
//            super(itemView);
//        }
//
//        @Override
//        public void bindData(Stock stock) {
//            this.stock = stock;
//            tv_name_stock.setText(stock.name);
//            //   tv_price_stock.setText(stock.name);
//        }
//
//        @Override
//        public void bindEvent() {
//            itemView.setOnClickListener(v -> {
//                listener.selectedStock(stock);
//            });
//        }
//    }
//}
//
