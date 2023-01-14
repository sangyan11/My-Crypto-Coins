package com.sangyan.cryptoapp.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sangyan.cryptoapp.R
import com.sangyan.cryptoapp.adapter.MarketAdapter
import com.sangyan.cryptoapp.adapter.WatchListAdapter
import com.sangyan.cryptoapp.apis.APiInterface
import com.sangyan.cryptoapp.apis.RetrofitInstance
import com.sangyan.cryptoapp.databinding.ActivityMainBinding
import com.sangyan.cryptoapp.databinding.FragmentMarketBinding
import com.sangyan.cryptoapp.databinding.FragmentWatchListBinding
import com.sangyan.cryptoapp.models.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WatchListFragment : Fragment() {
private lateinit var binding: FragmentWatchListBinding
    private lateinit var watchListItem : ArrayList<CryptoCurrency>
    private lateinit var watchList : ArrayList<String>
    private lateinit var adapter : WatchListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchListBinding.inflate(inflater,container,false)
           readData()
        lifecycleScope.launch(Dispatchers.IO){
            val  res = RetrofitInstance.getInstance().create(APiInterface::class.java).getMarketData()
            if(res.body()!=null){
                withContext(Dispatchers.Main){
                    watchListItem = ArrayList()
                    watchListItem.clear()
                    for (watchData in watchList){
                        for (item in res.body()!!.data.cryptoCurrencyList)
                            if(watchData==item.symbol){
                                watchListItem.add(item)
                            }
                    }
                    binding.spinKitView.visibility = GONE
                    binding.watchlistRecyclerView.adapter = MarketAdapter(requireContext(),watchListItem,"watchfragment")
                }
            }
        }
        return  binding.root
    }

    private fun readData() {
        val sharesPreferences = requireContext().getSharedPreferences("watchlist", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharesPreferences.getString("watchlist",ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>(){}.type
        watchList = gson.fromJson(json,type)
    }



}