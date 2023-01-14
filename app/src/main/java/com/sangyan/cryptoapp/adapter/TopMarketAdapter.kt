package com.sangyan.cryptoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sangyan.cryptoapp.R
import com.sangyan.cryptoapp.databinding.TopCurrencyLayoutBinding
import com.sangyan.cryptoapp.fragment.HomeFragmentDirections
import com.sangyan.cryptoapp.models.CryptoCurrency

class TopMarketAdapter (var context : Context, val list : List<CryptoCurrency>): RecyclerView.Adapter<TopMarketAdapter.ViewHolder>() {
    class ViewHolder(val binding : TopCurrencyLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           TopCurrencyLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
       )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.topCurrencyNameTextView.text = item.name
        Glide.with(context).load("https://s2.coinmarketcap.com/static/img/coins/64x64/"+item.id+".png")
            .into(holder.binding.topCurrencyImageView)

        if (item.quotes!![0].percentChange24h>0){
            holder.binding.topCurrencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
            holder.binding.topCurrencyChangeTextView.text = "+ ${String.format("%.02f",item.quotes[0].percentChange24h)} %"

        }
        else {
            holder.binding.topCurrencyChangeTextView.setTextColor(context.resources.getColor(R.color.res))
            holder.binding.topCurrencyChangeTextView.text = "${String.format("%.02f",item.quotes[0].percentChange24h)} %"
        }
        holder.itemView.setOnClickListener{
            Navigation.findNavController(it).navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item)
            )
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}