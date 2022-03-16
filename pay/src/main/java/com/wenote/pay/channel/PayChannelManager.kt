package com.wenote.pay.channel

import com.wenote.pay.log.debug


class PayChannelManager(val release:Boolean) {

    private var channels: LinkedHashMap<String, Channel> = LinkedHashMap()

    init {
        if (this.channels.size > 0) this.channels.clear()
    }

    fun getPaymentChannel(): LinkedHashMap<String, Channel> {
        return channels
    }

    fun getDefaultChannel():Channel? {
        for (channel in this.channels) {
            if(channel.value.channelType == ChannelType.DEFAULT) {
                return channel.value
            }
        }

        return null
    }

    fun resetChannel(channel:Channel) {
        for (c in channels) {
            if (channel.code == c.value.code) {
                debug("重新选择支付渠道 ${c.value.code} 默认支付渠道")
                c.value.channelType = ChannelType.DEFAULT
            }else {
                debug("重新选择支付渠道 ${c.value.code} 非默认支付渠道")
                c.value.channelType = ChannelType.OTHERS
            }
        }
    }

    fun addChannel(channel: Channel): PayChannelManager {
        if (!channels.containsKey(channel.code)) {
            channels[channel.code] = channel
        }

        return this@PayChannelManager
    }

    fun removeDefaultChannel(target: String) {
        removeChannel(target)
    }


    private fun removeChannel(name: String): PayChannelManager {
        if (channels.containsKey(name)) {
            channels.remove(name)
        }

        return this@PayChannelManager
    }
}
