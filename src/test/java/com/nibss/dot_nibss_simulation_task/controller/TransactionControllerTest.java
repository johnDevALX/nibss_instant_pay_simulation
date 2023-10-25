//package com.nibss.dot_nibss_simulation_task.controller;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.nibss.dot_nibss_simulation_task.dto.TransactionDto;
//import com.nibss.dot_nibss_simulation_task.model.Transaction;
//import com.nibss.dot_nibss_simulation_task.repository.TransactionRepository;
//import com.nibss.dot_nibss_simulation_task.util.HelperMethods;
//import com.nibss.dot_nibss_simulation_task.util.RequestTransactionDto;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.io.IOException;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class TransactionControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    TransactionRepository transactionRepository;
//    @Autowired
//    HelperMethods helperMethods;
//
//    Transaction transaction;
//
//    static final ObjectMapper mapper = createObjectMapper();
//
//
//    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
//        return mapper.writeValueAsBytes(object);
//    }
//
//    private static ObjectMapper createObjectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
//        mapper.registerModule(new JavaTimeModule());
//        return mapper;
//    }
//
//    private static final String SENDER_ACCOUNT = "123";
//    private static final String RECEIVER_ACCOUNT = "123";
//    private static final Double AMOUNT = 20.00;
//    private static final String DESCRIPTION = "123";
//
//
//    @BeforeEach
//    void setUp() {
//        transaction = createTransaction();
//        // You can set up data, mock services, etc., for your tests
//    }
//
//    private Transaction createTransaction(){
//        TransactionDto transactionDto = new TransactionDto();
//        transactionDto.setSenderAccount(SENDER_ACCOUNT);
//        transactionDto.setReceiverAccount(RECEIVER_ACCOUNT);
//        transactionDto.setAmount(AMOUNT);
//        transactionDto.setDescription(DESCRIPTION);
//        return helperMethods.createTransaction(transactionDto);
//    }
//
//
//    @Test
//    void testAcceptPayment() throws Exception {
//        // Create a TransactionDto object for the request
//        new TransactionDto();
//        TransactionDto transactionDto = helperMethods.map(transaction);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/v1/pay")
//                        .content(convertObjectToJsonBytes(transactionDto))  // Convert object to JSON
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().string("created"));
//    }
//
//    @Test
//    void testGenerateDailySummary() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/v1/generateDailySummary/2023-10-24")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isAccepted())
//                .andExpect(MockMvcResultMatchers.content().string("Your expected response content"));
//    }
//
//    @Test
//    void testRetrieveTransactions() throws Exception {
//        // Create a RequestTransactionDto object for the request
//        RequestTransactionDto requestTransactionDto = new RequestTransactionDto();
//        // Populate requestTransactionDto with necessary data
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/v1/retrieveTransactions")
//                        .content(asJsonString(requestTransactionDto))  // Convert object to JSON
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Your expected response content"));
//    }
//
//    // Helper method to convert objects to JSON
//    private String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
