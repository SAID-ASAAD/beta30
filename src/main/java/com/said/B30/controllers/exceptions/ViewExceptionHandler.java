package com.said.B30.controllers.exceptions;

import com.said.B30.businessrules.exceptions.DataEntryException;
import com.said.B30.businessrules.exceptions.DeletionNotAllowedException;
import com.said.B30.businessrules.exceptions.ResourceNotFoundException;
import com.said.B30.businessrules.services.ClientService;
import com.said.B30.businessrules.services.OrderService;
import com.said.B30.businessrules.services.ProductService;
import com.said.B30.controllers.ClientController;
import com.said.B30.controllers.OrderController;
import com.said.B30.controllers.ProductController;
import com.said.B30.dtos.clientdtos.ClientRequestDto;
import com.said.B30.dtos.clientdtos.ClientResponseDto;
import com.said.B30.dtos.clientdtos.ClientUpdateRequestDto;
import com.said.B30.dtos.orderdtos.OrderRequestDto;
import com.said.B30.dtos.orderdtos.OrderResponseDto;
import com.said.B30.dtos.orderdtos.OrderUpdateRequestDto;
import com.said.B30.dtos.productdtos.ProductFullResponseDto;
import com.said.B30.dtos.productdtos.ProductRequestDto;
import com.said.B30.dtos.productdtos.ProductSaleDto;
import com.said.B30.dtos.productdtos.ProductUpdateRequestDto;
import com.said.B30.infrastructure.enums.Category;
import com.said.B30.infrastructure.enums.OrderStatus;
import com.said.B30.infrastructure.enums.PaymentStatus;
import com.said.B30.infrastructure.enums.ProductStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@ControllerAdvice(assignableTypes = {ClientController.class, OrderController.class, ProductController.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ViewExceptionHandler {

    private final ClientService clientService;
    private final OrderService orderService;
    private final ProductService productService;

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception e, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("error"); // Página de erro genérica
        mv.addObject("errorMessage", "Erro inesperado: " + e.getMessage());
        mv.addObject("exception", e);
        return mv;
    }

    @ExceptionHandler(DeletionNotAllowedException.class)
    public ModelAndView deletionNotAllowed(DeletionNotAllowedException e, HttpServletRequest request) {
        String path = request.getRequestURI();
        String idStr = path.substring(path.lastIndexOf('/') + 1);
        Long id = Long.parseLong(idStr);
        ModelAndView mv;

        if (path.contains("/clients")) {
            ClientResponseDto client = clientService.findClientById(id);
            mv = new ModelAndView("clients/clientdetails");
            mv.addObject("client", client);
            mv.addObject("orders", orderService.findOrdersByClientId(id));
            mv.addObject("products", productService.findProductsByClientId(id));
        } else if (path.contains("/orders")) {
            OrderResponseDto order = orderService.findOrderById(id);
            mv = new ModelAndView("orders/order-details");
            mv.addObject("order", order);
            mv.addObject("client", clientService.findClientById(order.clientId()));
        } else { // Products
            ProductFullResponseDto product = productService.findProductById(id);
            mv = new ModelAndView("products/product-details");
            mv.addObject("product", product);
            // Cliente removido do produto
            // Adiciona DTO vazio para o modal de venda não quebrar
            mv.addObject("productSaleDto", new ProductSaleDto(null, null, null, null, null));
        }
        
        mv.addObject("errorMessage", e.getMessage());
        return mv;
    }

    @ExceptionHandler(DataEntryException.class)
    public ModelAndView dataEntryError(DataEntryException e, HttpServletRequest request) {
        String path = request.getRequestURI();
        ModelAndView mv;

        if (path.contains("/clients")) {
            mv = handleClientDataEntry(path, request);
        } else if (path.contains("/orders")) {
            mv = handleOrderDataEntry(path, request);
        } else { // Products
            mv = handleProductDataEntry(path, request);
        }

        mv.addObject("errorMessage", e.getMessage());
        return mv;
    }

    private ModelAndView handleClientDataEntry(String path, HttpServletRequest request) {
        if (path.contains("/register")) {
            ModelAndView mv = new ModelAndView("clients/clientregisterform");
            ClientRequestDto dto = new ClientRequestDto(
                request.getParameter("name"),
                request.getParameter("telephoneNumber"),
                request.getParameter("email"),
                request.getParameter("note")
            );
            mv.addObject("clientRequestDto", dto);
            return mv;
        } else {
            String idStr = path.substring(path.lastIndexOf('/') + 1);
            Long id = Long.parseLong(idStr);
            ModelAndView mv = new ModelAndView("clients/clientupdateform");
            mv.addObject("client", clientService.findClientById(id));
            mv.addObject("clientUpdateRequestDto", new ClientUpdateRequestDto(
                request.getParameter("name"),
                request.getParameter("telephoneNumber"),
                request.getParameter("email"),
                request.getParameter("note")
            ));
            return mv;
        }
    }

    private ModelAndView handleOrderDataEntry(String path, HttpServletRequest request) {
        if (path.contains("/register")) {
            ModelAndView mv = new ModelAndView("orders/order-register-form");
            OrderRequestDto dto = new OrderRequestDto(
                parseCategory(request.getParameter("category")),
                request.getParameter("description"),
                parseDate(request.getParameter("deliveryDate")),
                parseDouble(request.getParameter("establishedValue")),
                parseDouble(request.getParameter("deposit")),
                parseLong(request.getParameter("clientId"))
            );
            mv.addObject("orderRequestDto", dto);
            mv.addObject("categories", Category.values());
            return mv;
        } else {
            String idStr = path.substring(path.lastIndexOf('/') + 1);
            Long id = Long.parseLong(idStr);
            ModelAndView mv = new ModelAndView("orders/order-update-form");
            mv.addObject("order", orderService.findOrderById(id));
            mv.addObject("orderUpdateRequestDto", new OrderUpdateRequestDto(
                parseCategory(request.getParameter("category")),
                request.getParameter("description"),
                parseDate(request.getParameter("orderDate")),
                parseDate(request.getParameter("deliveryDate")),
                parseDate(request.getParameter("exitDate")), // Adicionado exitDate
                parseDouble(request.getParameter("establishedValue")),
                parseDouble(request.getParameter("externalServiceValue")),
                parseDouble(request.getParameter("materialValue")),
                request.getParameter("invoice"),
                request.getParameter("productionProcessNote"),
                request.getParameter("unexpectedIssue"), // Adicionado unexpectedIssue
                parseOrderStatus(request.getParameter("orderStatus")),
                parseLong(request.getParameter("clientId"))
            ));
            mv.addObject("categories", Category.values());
            mv.addObject("statuses", OrderStatus.values());
            return mv;
        }
    }

    private ModelAndView handleProductDataEntry(String path, HttpServletRequest request) {
        if (path.contains("/register")) {
            ModelAndView mv = new ModelAndView("products/product-register-form");
            ProductRequestDto dto = new ProductRequestDto(
                request.getParameter("description"),
                parseInt(request.getParameter("quantity")),
                request.getParameter("productionProcessNote"),
                parseDouble(request.getParameter("materialValue")),
                parseDouble(request.getParameter("externalServiceValue")),
                parseDouble(request.getParameter("preEstablishedValue"))
            );
            mv.addObject("productRequestDto", dto);
            return mv;
        } else if (path.contains("/sell")) {
            // Tratamento específico para erro na venda
            String idStr = path.substring(path.lastIndexOf('/') + 1);
            Long id = Long.parseLong(idStr);
            
            ModelAndView mv = new ModelAndView("products/product-details");
            ProductFullResponseDto product = productService.findProductById(id);
            mv.addObject("product", product);
            
            // Cliente removido do produto
            
            // Reconstrói o DTO de venda com os dados tentados
            Long clientId = parseLong(request.getParameter("clientId"));
            Integer quantity = parseInt(request.getParameter("quantity"));
            Double establishedValue = parseDouble(request.getParameter("establishedValue"));
            Double initialPayment = parseDouble(request.getParameter("initialPayment"));
            
            Double totalValue = null;
            if (quantity != null && establishedValue != null) {
                totalValue = quantity * establishedValue;
            }

            ProductSaleDto saleDto = new ProductSaleDto(
                clientId,
                quantity,
                establishedValue,
                totalValue,
                initialPayment
            );
            mv.addObject("productSaleDto", saleDto);
            
            return mv;
        } else {
            // Edição
            String idStr = path.substring(path.lastIndexOf('/') + 1);
            Long id = Long.parseLong(idStr);
            ModelAndView mv = new ModelAndView("products/product-update-form");
            mv.addObject("product", productService.findProductById(id));
            mv.addObject("productUpdateRequestDto", new ProductUpdateRequestDto(
                request.getParameter("description"),
                parseInt(request.getParameter("quantity")),
                request.getParameter("productionProcessNote"),
                parseDate(request.getParameter("productionDate")),
                // saleDate removido
                parseDouble(request.getParameter("materialValue")),
                parseDouble(request.getParameter("externalServiceValue")),
                parseDouble(request.getParameter("preEstablishedValue")),
                // establishedValue removido
                parseProductStatus(request.getParameter("productStatus"))
                // invoice removido
                // clientId removido
            ));
            mv.addObject("productStatuses", ProductStatus.values());
            // paymentStatuses removido
            return mv;
        }
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String path = request.getRequestURI();
        ModelAndView mv;
        
        if (path.contains("/clients")) {
            mv = new ModelAndView("clients/clients");
            mv.addObject("clients", clientService.findAllClientsPaginated(PageRequest.of(0, 8, Sort.by("id").descending())));
        } else if (path.contains("/orders")) {
            mv = new ModelAndView("orders/orders-page");
            mv.addObject("orders", orderService.findAllOrdersPaginated(PageRequest.of(0, 8)));
        } else {
            mv = new ModelAndView("products/products-page");
            mv.addObject("products", productService.findAllProductsPaginated(PageRequest.of(0, 8, Sort.by("id").descending())));
        }
        
        mv.addObject("errorMessage", e.getMessage());
        return mv;
    }

    // Helpers para parsing
    private Double parseDouble(String value) {
        try { return value != null && !value.isEmpty() ? Double.parseDouble(value) : null; } catch (NumberFormatException e) { return null; }
    }
    private Long parseLong(String value) {
        try { return value != null && !value.isEmpty() ? Long.parseLong(value) : null; } catch (NumberFormatException e) { return null; }
    }
    private Integer parseInt(String value) {
        try { return value != null && !value.isEmpty() ? Integer.parseInt(value) : null; } catch (NumberFormatException e) { return null; }
    }
    private LocalDate parseDate(String value) {
        try { return value != null && !value.isEmpty() ? LocalDate.parse(value) : null; } catch (Exception e) { return null; }
    }
    private Category parseCategory(String value) {
        try { return value != null ? Category.valueOf(value) : null; } catch (Exception e) { return null; }
    }
    private OrderStatus parseOrderStatus(String value) {
        try { return value != null ? OrderStatus.valueOf(value) : null; } catch (Exception e) { return null; }
    }
    private ProductStatus parseProductStatus(String value) {
        try { return value != null ? ProductStatus.valueOf(value) : null; } catch (Exception e) { return null; }
    }
}
