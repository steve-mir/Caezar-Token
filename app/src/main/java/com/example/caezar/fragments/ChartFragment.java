package com.example.caezar.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.caezar.R;
import com.example.caezar.activities.PriceHistoryActivity;
import com.example.caezar.adapters.CurrencyAdapter;
import com.example.caezar.models.Currency;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment {
    private static final NavigableMap<Double, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000D, "k");
        suffixes.put(1_000_000D, "M");
        suffixes.put(1_000_000_000D, "G");
        suffixes.put(1_000_000_000_000D, "T");
        suffixes.put(1_000_000_000_000_000D, "P");
        suffixes.put(1_000_000_000_000_000_000D, "E");
    }
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    //"https://api.cryptowat.ch/markets/binance/btcusdt"
    
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private ArrayList<Currency> currencyArrayList;
    private CurrencyAdapter adapter;

    private String logoURL = "https://s2.coinmarketcap.com/static/img/coins/64x64/";//1.png";

    ConstraintLayout layoutMiscellaneous;
    BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior;
    TextView nameDesc, symbolDesc, description, rankText, marketCap, priceTextDec;
    ImageView symbolImage;

    public ChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartFragment newInstance(String param1, String param2) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.currenciesList);
        layoutMiscellaneous = view.findViewById(R.id.layoutMiscellaneous);
        //progressBar = view.findViewById(R.id.progress);

        currencyArrayList = new ArrayList<>();
        adapter = new CurrencyAdapter(currencyArrayList, getContext(), new CurrencyAdapter.ItemClickListener() {
            @Override
            public void onItemClickedListener(String id, int pos) {
                String url2 = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/info?&id=";
                RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
                JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url2+id, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject dataObj = response.getJSONObject("data");
                            JSONObject dataForObj = dataObj.getJSONObject(id);
                            String desc = dataForObj.getString("description");
                            String nm = dataForObj.getString("name");
                            String sym = dataForObj.getString("symbol");
                            String logo = dataForObj.getString("logo");

                            initBottomSheet(nm, sym, desc, currencyArrayList.get(pos).getRank(), currencyArrayList.get(pos).getMarketCap(),
                                    currencyArrayList.get(pos).getPrice(),logo, currencyArrayList.get(pos).getPercentChange24(), currencyArrayList.get(pos).getVol());

                            if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                //TextView heading = layoutMiscellaneous.findViewById(R.id.bottomSheetText);
                                //heading.setText("My Notebooks");
                            }else {
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            }


                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Failed...", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        //progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), " Failure "+error, Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("X-CMC_PRO_API_KEY", "282a8aa9-d19e-4eab-822c-2c81bcfe2f1b");
                        return headers;
                    }
                };
                requestQueue2.add(jsonObjectRequest2);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void initBottomSheet(String name, String symbol, String desc, int rank, double markCap, double price, String img, double pChange, double tVol) {
        bottomSheetBehavior = BottomSheetBehavior.from(layoutMiscellaneous);

        nameDesc = layoutMiscellaneous.findViewById(R.id.name_desc);
        symbolDesc = layoutMiscellaneous.findViewById(R.id.symbol_desc);
        description = layoutMiscellaneous.findViewById(R.id.description);

        rankText = layoutMiscellaneous.findViewById(R.id.rank_desc);
        marketCap = layoutMiscellaneous.findViewById(R.id.mark_cap_desc);
        priceTextDec = layoutMiscellaneous.findViewById(R.id.price_desc);

        symbolImage = layoutMiscellaneous.findViewById(R.id.logo_desc);

        nameDesc.setText(name);
        symbolDesc.setText(symbol);
        description.setText(desc);
        rankText.setText(String.valueOf(rank));

        marketCap.setText(String.valueOf(Math.round(markCap)));//abbrevNumber(Math.round(markCap)));
        priceTextDec.setText("$ " + df2.format(price));

        Glide.with(getContext()).load(img).into(symbolImage);


        layoutMiscellaneous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        layoutMiscellaneous.findViewById(R.id.priceBtn).setOnClickListener(v -> {
            // https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/historical  - for getting weekly price
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            Intent intent = new Intent(getContext(), PriceHistoryActivity.class);
            intent.putExtra("img", img);
            intent.putExtra("price", price);
            intent.putExtra("pChange", pChange);
            intent.putExtra("vol", tVol);
            intent.putExtra("mCap", markCap);
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getCurrencyData();
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    private void getCurrencyData(){
        //progressBar.setVisibility(View.VISIBLE);
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        //key - 282a8aa9-d19e-4eab-822c-2c81bcfe2f1b
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //progressBar.setVisibility(View.GONE);
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {

                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String name = dataObj.getString("name");
                        String symbol = dataObj.getString("symbol");
                        int rnk = dataObj.getInt("cmc_rank");


                        int id = dataObj.getInt("id");
                        String logoLink = logoURL + id + ".png";


                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");

                        double price= USD.getDouble("price");
                        double vol = USD.getDouble("volume_24h");
                        double perChange = USD.getDouble("percent_change_1h");
                        double mrkCap = USD.getDouble("market_cap");
                        double perChange24 = USD.getDouble("percent_change_24h");

                        Currency currency = new Currency( String.valueOf(id),name, symbol, logoLink, price, vol, perChange);
                        currency.setRank(rnk);
                        currency.setMarketCap(mrkCap);
                        currency.setPercentChange24(perChange24);

                        currencyArrayList.add(currency);
                    }
                    adapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed...", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), error+ " Failed to get the data...", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY", "282a8aa9-d19e-4eab-822c-2c81bcfe2f1b");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);





    }

    public static String abbrevNumber(double value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return abbrevNumber(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + abbrevNumber(-value);
        if (value < 1000) return Double.toString(value); //deal with easy case

        Map.Entry<Double, String> e = suffixes.floorEntry(value);
        Double divideBy = e.getKey();
        String suffix = e.getValue();

        double truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}