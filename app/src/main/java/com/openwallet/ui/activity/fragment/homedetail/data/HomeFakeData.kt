package com.openwallet.ui.activity.fragment.homedetail.data

import com.openwallet.R
import com.openwallet.ui.activity.fragment.home.HomeBanner
import com.openwallet.ui.activity.fragment.homedetail.HomeDetailHeaderBean
import com.openwallet.ui.activity.fragment.homedetail.ImageBean
import com.openwallet.ui.activity.fragment.homedetail.TextBean
import com.openwallet.ui.activity.fragment.homedetail.TitleTextBean

object HomeFakeData {

    fun getHomeBannerList() = listOf(
        HomeBanner(
            imageResId = R.drawable.home_banner_img1,
            "What are NFTs?",
            "Discover the world of NFTs with . "
        ),
        HomeBanner(
            imageResId = R.drawable.home_banner_img3,
            "Why you should be excited about Gold X NFT.",
            "Discover Gold X NFT Campaign with .   "
        ),
        HomeBanner(
            imageResId = R.drawable.home_banner_img2,
            "What is Web 3.0?",
            "Discover the next generation of the internet with .  "
        )
    )

    fun getDetailData1(): List<Any> {
        val items = ArrayList<Any>()
        items.add(
            HomeDetailHeaderBean(
                "What are NFTs?",
                R.drawable.home_detail_header_img1_new,
                "Discover the world of NFTs with . "
            )
        )
        items.add(TitleTextBean("Deciphering Non-Fungible Tokens?"))
        items.add(TitleTextBean("What are NFTs?", 24f, null, 16f))
        items.add(TextBean("NFTs – or Non-fungible Tokens – are unique digital assets stored on the blockchain. They are designed to be unique and cannot be exchanged for an equivalent, unlike other digital currencies and assets like Bitcoin or Central Bank Digital Currencies. \n "))


        items.add(ImageBean(R.drawable.home_detail_img1_1))
        items.add(TextBean("The NFTs can be used to represent distinct goods, such as a land title deed or a particular piece of artwork.  They can be digital proof of ownership or authenticity and can even provide transferability when a physical good/serivce is not as easily transferable.  \n\n  In relation to  Open App and the associated Campaign; the NFT does not represent ownership of any good or service and is non-tradable, meaning you will not be able to use or sell it on any exchange.  \n\n  However, you will learn more about NFTs and Web 3.0 and will play a crucial role in ’s  creation of an NFT while you redeem your physical Gold Gift. \n  "))
        items.add(ImageBean(R.drawable.home_detail_img1_2))
        items.add(TitleTextBean("Why are NFTs so Interesting? "))
        items.add(TextBean("NFTs are exciting because they give real assets brand-new attributes that didn't previously exist. For example, tokenising less liquid, real-world assets like artwork, jewelery, real estate, or even private equity, may add more liquidity to those assets.  \n\n  Tradeable NFTs on exchanges also allow more people to transact in a market. Since records can be included into the smart contract on the blockchain, it overcomes issues like separately proving ownership or authenticity. For instance, an NFT that represents a real-world piece of art or jewellery can store information on the asset's origin and ownership history. \n\n"))

        return items
    }


    fun getDetailData2(): List<Any> {
        val items = ArrayList<Any>()
        items.add(
            HomeDetailHeaderBean(
                "Why you should be excited about Gold X NFT? ",
                R.drawable.home_detail_header_img1,
                "Discover Gold X NFT Campaign with ."
            )
        )
        items.add(TitleTextBean("Excited about NFTs?"))
        items.add(TextBean("We are extremely excited about your registration and your interest in the Web 3.0 world. As a first step into the world of digital assets, blockchain, and Web 3.0, we are launching Gold X NFT.  Along with your learnings about  NFTs, we hope you enjoy using our  Open app, too. We have implemented some new technologies that are being used in the background, such as a digital wallet to store NFTs and an in-house process to create (or \"mint\") these NFTs.   \n"))


        items.add(ImageBean(R.drawable.home_detail_img3_1))
        items.add(TextBean("\n So why be excited? You'll notice several intriguing features if you examine  Open closely.  When you request redemption of your dedicated gift, you will also initiate the creation of the NFT for . The NFT will land in the wallet to be stored and marked redeemed, as per the smart contract instruction.  \n\n "))

        return items
    }


    fun getDetailData3(): List<Any> {
        val items = ArrayList<Any>()
        items.add(
            HomeDetailHeaderBean(
                "What is Web 3.0?",
                R.drawable.home_detail_header_img2,
                "Discover the next generation of the internet with ."
            )
        )
        items.add(TitleTextBean("Know about the Web 3.0 revolution? "))
        items.add(TitleTextBean("What is Web 3.0? ", 24f, null, 16f))
        items.add(TextBean("The Web is a common concept. But what is Web 3.0? Or even Web 1.0 and 2.0? \n"))

        items.add(ImageBean(R.drawable.home_detail_img2_1))
        items.add(TextBean(" \n Web 1.0 dates back to the 1990s. It refers to  the first internet revolution which is mainly characterised by websites (anything starting with http://) that people still browse. The advent of Web 2.0 in the early 2000s focused on exclusive, centralised applications like social media (e.g., Facebook) or banking apps (e.g. ).  \n\n  Web 3.0 emerged around 2020. Built on the technology called blockchain, it gained a lot of popularity because of Bitcoin. To understand this revolution, you need to understand the concept of a distributed ledger.  \n "))
        items.add(ImageBean(R.drawable.home_detail_img2_2))
        items.add(TitleTextBean("What is the difference between Centralised and Distributed Ledgers? "))
        items.add(TextBean("Today, if you want to make a purchase of a good or a service, you would enter a store (or go online ) and pay with a bank card. The shopkeeper would note your purchase and apply the transaction to your card. Your account would then be debited after your bank verifies the purchase. This is accomplished through a centralized ledger that records your transactions, and your bank is instrumental in validating them. \n"))
        items.add(ImageBean(R.drawable.home_detail_img2_3))

        items.add(TextBean("\n Compare this to a distributed ledger like blockchain technology. With a distributed ledger system, you could pay the shopkeeper directly for what you bought, bypassing the requirement for a bank to function as an intermediary to verify purchases. The transparency and traceability of transactions on the blockchain allows immediate verification and validation, so intermediaries are no longer needed. \n"))

        items.add(TitleTextBean("What is Blockchain?  "))
        items.add(TextBean("Blockchain is a technology that maintains transaction records not just in one computer in a network, but across several. The word \"blockchain\" comes from the fact that each transaction is stored as a \"block\" and linked together to form a \"chain\". This indicates that the ledger is open to all participants, allowing them to view previous events including transactions. \n\n Participants in blockchain benefit from having access to precise and fast information about transactions. Theoretically, it is safer because it is far more difficult for malicious users to simultaneously hack multiple computers in one go. Furthermore, since the system is decentralized, documented and not codified, transactions can happen more quickly. \n"))

        items.add(TitleTextBean("How can Blockchain technology be used?"))
        items.add(TextBean("Blockchain is built on transparency and traceability and this technology has three uses: "))
        items.add(TitleTextBean("<b>1.Payments</b>", 16f, 16f))
        items.add(TextBean("Blockchains are one option to deliver payments more efficiently and with fewer intermediaries. A major benefit can be found in cross-border payments as they can be done bilaterally and in theory (still not achieved) more quickly and cheaply than today’s methods (e.g., using correspondent banking relationships with conventional payment methods).  Through the use of digital wallets, crypto-tokens like Bitcoin and Ethereum also enable direct value transfers between individuals. "))

        items.add(TitleTextBean("<b>2.Digital Transaction Record</b>", 16f, 16f))
        items.add(TextBean("Blockchains can be used to hold non-fungible tokens (NFTs).  NFTs are essentially digital tokens that can represent real-world assets like jewelry and artwork. "))

        items.add(TitleTextBean("<b>3.Smart Contracts</b>", 16f, 16f))
        items.add(TextBean("Blockchain can incorporate smart contracts, that, for example, can hard-code a tokenised bond to pay interest payments to its holder. The full potential of smart contracts is still unexplored: they could one day record, document, and execute contracts or agreements without any trusted intermediary or arbitrator. \n"))

        return items
    }
}

