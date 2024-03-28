package by.kirilldikun.onlinestoreapi.service.impl;

import by.kirilldikun.onlinestoreapi.dto.ProductDto;
import by.kirilldikun.onlinestoreapi.entity.PriceChange;
import by.kirilldikun.onlinestoreapi.repository.CategoryRepository;
import by.kirilldikun.onlinestoreapi.repository.PriceChangeRepository;
import by.kirilldikun.onlinestoreapi.service.ProductService;
import by.kirilldikun.onlinestoreapi.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final PriceChangeRepository priceChangeRepository;

    private final CategoryRepository categoryRepository;

    private final ProductService productService;

    @Override
    @Transactional(readOnly = true)
    public byte[] createPriceChart(Long productId) {
        List<PriceChange> priceChanges = priceChangeRepository.findAllByProductId(productId);
        ProductDto productDto = productService.findById(productId);
        List<Date> changeDates = new ArrayList<>();
        changeDates.add(Date.from(productDto.getCreatedAt().toInstant()));
        List<BigDecimal> prices = new ArrayList<>();
        if (priceChanges.isEmpty()) {
            changeDates.add(Date.from(Instant.now()));
            prices.add(productDto.getPrice());
            prices.add(productDto.getPrice());
        } else {
            prices.add(priceChanges.get(0).getPriceBefore());
            for (PriceChange priceChange : priceChanges) {
                changeDates.add(Date.from(priceChange.getChangeData().toInstant()));
                prices.add(priceChange.getPriceAfter());
            }
        }
        XYChart chart = createChart(changeDates, prices, productDto.getName());
        return writeChartToPNG(chart);
    }

    private XYChart createChart(List<Date> changeDates, List<BigDecimal> prices, String productName) {
        XYChart chart = new XYChartBuilder().width(800).height(600).title("График изменения цены").xAxisTitle("Дата").yAxisTitle("Цена").build();
        XYSeries series = chart.addSeries("Цена товара %s".formatted(productName), changeDates, prices);
        series.setMarker(SeriesMarkers.CIRCLE);
        return chart;
    }

    private byte[] writeChartToPNG(XYChart chart) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            BitmapEncoder.saveBitmap(chart, outputStream, BitmapEncoder.BitmapFormat.PNG);
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error("Exception while creating chart", e);
            throw new RuntimeException("Exception while creating chart");
        }
    }

    private byte[] writeChartToPNG(PieChart chart) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            BitmapEncoder.saveBitmap(chart, outputStream, BitmapEncoder.BitmapFormat.PNG);
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error("Exception while creating chart", e);
            throw new RuntimeException("Exception while creating chart");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] createCategoriesRevenue() {
        PieChart chart = new PieChartBuilder().width(800).height(600).title("Выручка по категориям").build();
        categoryRepository.getCategoriesTotalRevenue()
                .forEach(dto -> chart.addSeries(dto.categoryName(), dto.totalRevenue()));
        return writeChartToPNG(chart);
    }
}
