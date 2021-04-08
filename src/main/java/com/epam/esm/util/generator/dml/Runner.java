package com.epam.esm.util.generator.dml;

import com.epam.esm.util.generator.dml.tableGenerators.GiftCertificateGenerator;
import com.epam.esm.util.generator.dml.tableGenerators.ItemOrderGenerator;
import com.epam.esm.util.generator.dml.tableGenerators.JoinTagGiftCertificateGenerator;
import com.epam.esm.util.generator.dml.tableGenerators.OrderDetailsGenerator;
import com.epam.esm.util.generator.dml.tableGenerators.TagGenerator;
import com.epam.esm.util.generator.dml.tableGenerators.UserGenerator;

import java.util.List;

public class Runner {
    public static void main(String[] args) {
        int numberOfGiftCertificates = 1000;
        int numberOfTags = 1000;
        int numberOfUsers = 1000;
        int numberOfOrders = 10000;
        List<String> giftCertificatesQueries = GiftCertificateGenerator.generateInsertQueries(numberOfGiftCertificates);
        List<String> tagQueries = TagGenerator.generateInsertQueries(numberOfTags);
        List<String> joinQueries = JoinTagGiftCertificateGenerator.generateInsertQueries(numberOfGiftCertificates, numberOfTags);
        List<String> userQueries = UserGenerator.generateInsertQueries(numberOfUsers);
        List<String> itemOrderQueries = ItemOrderGenerator.generateInsertQueries(numberOfOrders, numberOfUsers);
        List<String> orderDetailsQueries = OrderDetailsGenerator.generateInsertQueries(numberOfOrders, numberOfGiftCertificates);
        List<String> updateOrderTotalCost = ItemOrderGenerator.generateTotalCostUpdateQueries(numberOfOrders);
        QueriesOutputWriter.saveQueries("dml/tag.sql", tagQueries);
        QueriesOutputWriter.saveQueries("dml/gc.sql", giftCertificatesQueries);
        QueriesOutputWriter.saveQueries("dml/gct.sql", joinQueries);
        QueriesOutputWriter.saveQueries("dml/user.sql", userQueries);
        QueriesOutputWriter.saveQueries("dml/itemOrder.sql", itemOrderQueries);
        QueriesOutputWriter.saveQueries("dml/orderDetails.sql", orderDetailsQueries);
        QueriesOutputWriter.saveQueries("dml/setTotalCost.sql", updateOrderTotalCost);
    }
}
