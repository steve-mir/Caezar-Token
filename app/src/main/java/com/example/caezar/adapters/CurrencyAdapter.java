package com.example.caezar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.caezar.R;
import com.example.caezar.models.Currency;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    private ArrayList<Currency> currencies;
    private Context context;
    private ItemClickListener itmeClickListener;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public CurrencyAdapter(ArrayList<Currency> currencies, Context context, ItemClickListener itmeClickListener) {
        this.currencies = currencies;
        this.context = context;
        this.itmeClickListener = itmeClickListener;

    }

    @NonNull
    @Override
    public CurrencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_currencies, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyAdapter.ViewHolder holder, int position) {
        Currency currency = currencies.get(position);
        //holder.name.setText(currency.getName());
        holder.symbol.setText(currency.getSymbol());
        holder.rate.setText("$ " + df2.format(currency.getPrice()));

        holder.vol.setText("Vol. " + df2.format(currency.getVol()));

        if (currency.getPercentChange1() < 0){
            holder.percent.setTextColor(context.getResources().getColor(R.color.red_active));
        }else {
            holder.percent.setTextColor(context.getResources().getColor(R.color.green_active));
        }
        holder.percent.setText(df2.format(currency.getPercentChange1()));

        Glide.with(context).load(currency.getLogo())/*.placeholder(Util.setPlaceholderImage(fullName, 2))*/.into(holder.logo);

        holder.currencyLayout.setOnClickListener(v -> itmeClickListener.onItemClickedListener(currency.getId(), position));

    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView currencyLayout;
        private TextView  symbol, rate, percent, vol;
        private RoundedImageView logo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //name = itemView.findViewById(R.id.name);
            currencyLayout = itemView.findViewById(R.id.currencyLayout);
            symbol = itemView.findViewById(R.id.symbol);
            rate = itemView.findViewById(R.id.rate);
            percent = itemView.findViewById(R.id.percentChange);
            logo = itemView.findViewById(R.id.logo);
            vol = itemView.findViewById(R.id.vol);
        }
    }

    public interface ItemClickListener {
        void onItemClickedListener(String id, int pos);
    }
}
