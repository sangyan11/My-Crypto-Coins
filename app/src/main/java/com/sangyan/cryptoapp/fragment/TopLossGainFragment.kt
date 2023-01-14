package com.sangyan.cryptoapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.sangyan.cryptoapp.adapter.MarketAdapter
import com.sangyan.cryptoapp.apis.APiInterface
import com.sangyan.cryptoapp.apis.RetrofitInstance
import com.sangyan.cryptoapp.databinding.FragmentTopLossGainBinding
import com.sangyan.cryptoapp.models.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections


class TopLossGainFragment : Fragment() {
private lateinit var binding : FragmentTopLossGainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopLossGainBinding.inflate(layoutInflater)
        getMarketData()
               return binding.root
    }

    private fun getMarketData() {
        val  position = requireArguments().getInt("position")
      lifecycleScope.launch(Dispatchers.IO){
          val res = RetrofitInstance.getInstance().create(APiInterface::class.java).getMarketData()
          if (res.body()!=null){
              withContext(Dispatchers.Main){
                  val dataItem = res.body()!!.data.cryptoCurrencyList
                  Collections.sort(dataItem){
                      o1,o2 -> (o2.quotes[0].percentChange24h.toInt())
                      .compareTo(o1.quotes[0].percentChange24h.toInt())
                  }

                  val list = ArrayList<CryptoCurrency>()

                  if (position == 0){
                      binding.spinKitView.visibility = GONE
                      list.clear()
                      for (i in 0..9) {
                          list.add(dataItem[i])
                      }
                      binding.topGainLoseRecyclerView.adapter = MarketAdapter(
                          requireContext(),
                          list,
                          "home"
                      )

                      }
                  else {
                      binding.spinKitView.visibility = GONE
                      list.clear()
                      for (i in 0..9) {
                          list.add(dataItem[dataItem.size-1-i])
                      }
                      binding.topGainLoseRecyclerView.adapter = MarketAdapter(
                          requireContext(),
                          list,
                          "home"
                      )
                  }

              }
          }
      }
    }




}