package com.wenote.pay

import com.wenote.pay.channel.Channel
import com.wenote.pay.env.RunEnvironment

object WPay {
    private val defaultPayChannel = ArrayList<Channel>()
    private val payChannelHash = ArrayList<Int>()

    fun debug(): RunEnvironment {
        return RunEnvironment(false)
    }

    fun release(): RunEnvironment {
        return RunEnvironment(true)
    }

    fun auto(): RunEnvironment {
        return RunEnvironment(!BuildConfig.DEBUG)
    }

    fun addDefaultChannel(channel: Channel) {
        val hash = channel.icon
        com.wenote.pay.log.debug("channel hashcode $hash ${channel.name}")
        if (hash in payChannelHash) return

        payChannelHash.add(hash)
        defaultPayChannel.add(channel)
    }

    internal fun getDefaultChannel(): ArrayList<Channel> {
        return defaultPayChannel
    }

}