package com.wenote.pay.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wenote.pay.R
import com.wenote.pay.channel.Channel
import com.wenote.pay.channel.ChannelType

abstract class PayChannelAdapter : RecyclerView.Adapter<PayChannelAdapter.ChannelViewHolder>() {

    private var channels: ArrayList<Channel> = ArrayList()


    fun setChannels(data: ArrayList<Channel>) {
        channels.clear()
        channels.addAll(data)
        notifyDataSetChanged()
    }

    abstract fun onItemClick(channel:Channel)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_channel, parent, false)
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holderChannel: ChannelViewHolder, position: Int) {
        holderChannel.setView(channels[position])
    }

    override fun getItemCount(): Int {
        return channels.size
    }

    inner class ChannelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var icon: ImageView? = null
        private var channel: TextView? = null
        private var status: ImageView? = null

        init {
            icon = itemView.findViewById(R.id.image)
            channel = itemView.findViewById(R.id.channelName)
            status = itemView.findViewById(R.id.checked)
        }

        fun setView(channel: Channel) {
            icon?.setBackgroundResource(channel.icon)
            this.channel?.text = channel.name
            status?.visibility = if (channel.channelType == ChannelType.DEFAULT) {
                View.VISIBLE
            } else {
                View.GONE
            }

            itemView.findViewById<View>(R.id.layout).setOnClickListener {
                onItemClick(channel)
            }
        }
    }
}