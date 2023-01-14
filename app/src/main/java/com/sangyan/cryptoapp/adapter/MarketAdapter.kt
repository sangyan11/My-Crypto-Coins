package com.sangyan.cryptoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sangyan.cryptoapp.R
import com.sangyan.cryptoapp.databinding.CurrencyItemLayoutBinding
import com.sangyan.cryptoapp.fragment.HomeFragmentDirections
import com.sangyan.cryptoapp.fragment.MarketFragment
import com.sangyan.cryptoapp.fragment.MarketFragmentDirections
import com.sangyan.cryptoapp.fragment.WatchListFragmentDirections
import com.sangyan.cryptoapp.models.CryptoCurrency

class MarketAdapter(var context: Context, var list: List<CryptoCurrency>, var type: String) : RecyclerView.Adapter<MarketAdapter.ViewHolder>() {
    class ViewHolder(val binding : CurrencyItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){}
      fun updateList(dataItem  : List<CryptoCurrency>){
          list = dataItem
          notifyDataSetChanged()
      }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CurrencyItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
      }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.currencyNameTextView.text = item.name
        holder.binding.currencySymbolTextView.text = item.symbol
        holder.binding.currencyPriceTextView.text = "${String.format("$%.2f",item.quotes[0].price)}"
        Glide.with(context).load("https://s2.coinmarketcap.com/static/img/coins/64x64/"+item.id+".png")
            .into(holder.binding.currencyImageView)
        if (item.quotes!![0].percentChange24h>0){
            holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
            holder.binding.currencyChangeTextView.text = "+ ${String.format("%.02f",item.quotes[0].percentChange24h)} %"

        }
        else {
            holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.res))
            holder.binding.currencyChangeTextView.text = "${String.format("%.02f",item.quotes[0].percentChange24h)} %"
        }
        Glide.with(context).load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/"+item.id+".png").thumbnail(Glide.with(context).load(
            R.drawable.spinner))
            .into(holder.binding.currencyChartImageView)
        holder.itemView.setOnClickListener{
            if (type=="home") {

                findNavController(it).navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item)
                )
            }
            else if (type == "market"){
                findNavController(it).navigate(
                    MarketFragmentDirections.actionMarketFragmentToDetailsFragment(item)
                )
            }
            else {
                findNavController(it).navigate(
                    WatchListFragmentDirections.actionWatchListFragmentToDetailsFragment(item)
                )
            }
        }

    }

    override fun getItemCount(): Int {
      return list.size
    }
}