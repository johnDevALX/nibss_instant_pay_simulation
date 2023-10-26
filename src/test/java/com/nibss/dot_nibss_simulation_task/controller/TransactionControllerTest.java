package com.nibss.dot_nibss_simulation_task.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nibss.dot_nibss_simulation_task.dto.TransactionDto;
import com.nibss.dot_nibss_simulation_task.model.Transaction;
import com.nibss.dot_nibss_simulation_task.service.TransactionService;
import com.nibss.dot_nibss_simulation_task.util.HelperMethods;
import com.nibss.dot_nibss_simulation_task.util.RequestTransactionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    HelperMethods helperMethods;

    Transaction transaction;
    TransactionDto transactionDto;


    private static final String SENDER_ACCOUNT = "123";
    private static final String RECEIVER_ACCOUNT = "123";
    private static final Double AMOUNT = 20.00;
    private static final String DESCRIPTION = "123";


    @BeforeEach
    void setUp() {
        transaction = createTransaction();
        transactionDto = helperMethods.mapToTransactionDto(transaction);
    }

    private Transaction createTransaction(){
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setSenderAccount(SENDER_ACCOUNT);
        transactionDto.setReceiverAccount(RECEIVER_ACCOUNT);
        transactionDto.setAmount(AMOUNT);
        transactionDto.setDescription(DESCRIPTION);
        return helperMethods.mapToTransactionObj(transactionDto);
    }


    @Test
    void testAcceptPayment() throws Exception {
        given(transactionService.processTransaction(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/v1/pay")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transactionDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testGenerateDailySummary() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/generateDailySummary/2023-10-25")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().string("success"));
    }

    @Test
    void testRetrieveTransactions() throws Exception {
        RequestTransactionDto requestTransactionDto = new RequestTransactionDto();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/retrieveTransactions")
                        .content(asJsonString(requestTransactionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Retrieved"));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
