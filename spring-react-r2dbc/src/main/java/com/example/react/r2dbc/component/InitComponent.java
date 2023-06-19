package com.example.react.r2dbc.component;

import com.example.react.r2dbc.dto.StockInfoDto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * 데이터 초기화 컴포넌트
 * 결합성 제거를 위해 dto 객체만 핸들링
 */
@Component
public class InitComponent {
    Random random = new Random();

    public double nextGaussian() {
        return random.nextGaussian();
    }

    public Integer getRandomView() {
        return (int) (Math.abs(nextGaussian()) * 100);
    }

    public Long getRandomVolume(Integer price) {
        return (long) (Math.abs(nextGaussian()) * 1000 * price);
    }

    public Integer getRandomPrice(Integer price) {
        double percent = (random.nextGaussian()) * 15; // 분산값 조절
        if (percent > 30.0) percent = 30.0;
        else if (percent < -30.0) percent = -30.0;
        price += (int) (price * percent / 100); // 변동률만큼 변동
        price -= (price % quoteUnit(price)); // 호가단위로 맞춤
        if (percent < 0)
            price += quoteUnit(price);
        return price;
    }

    // 호가단위
    public Integer quoteUnit(Integer price) {
        if (price < 2000) return 1;
        else if (price < 5000) return 5;
        else if (price < 20000) return 10;
        else if (price < 50000) return 50;
        else if (price < 200000) return 100;
        else if (price < 500000) return 500;
        else return 1000;
    }

    // init data
    public List<StockInfoDto> initData() {
        List<StockInfoDto> result = new ArrayList<>();
        try {
            InputStream inputStream = new ClassPathResource("SampleData.csv").getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = br.readLine(); // 헤더 제거
            while ((line = br.readLine()) != null) {
                String stringArray[] = line.split(",");
                StockInfoDto stockInfo = StockInfoDto.builder()
                        .id(Long.valueOf(stringArray[0]))
                        .code(stringArray[1])
                        .name(stringArray[2])
                        .price(Integer.valueOf(stringArray[3]))
                        .build();
                RandomValueDto randomValueDto = generateRandomValue(stockInfo.getPrice());
                stockInfo.update(randomValueDto);
                result.add(stockInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public RandomValueDto generateRandomValue(Integer price) {
        Integer randomViews = getRandomView();
        Long randomVolume = getRandomVolume(price);
        Integer randomPrice = getRandomPrice(price);
        RandomValueDto resultDto = new RandomValueDto(randomViews, randomVolume, randomPrice, System.currentTimeMillis());
        return resultDto;
    }
}