package com.agile.demo;

import com.agile.demo.api.sample.SampleEntity;
import com.agile.demo.api.sample.SampleRepository;
import com.agile.demo.biz.account.AccountEntity;
import com.agile.demo.biz.account.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component 
@AllArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationRunner {

	private AccountRepository accountRepository;
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		accountRepository.save(AccountEntity.builder()
						.userId("test1")
						.password(passwordEncoder.encode("test2"))
						.role("SUPER")
						.email("test@test.com")
						.name("test")
						.phone("010-000-0000")
				.build());
		accountRepository.save(AccountEntity.builder()
				.userId("test2")
				.password(passwordEncoder.encode("test2"))
				.role("SUPER")
				.email("test@test.com")
				.name("test")
				.phone("010-000-0000")
				.build());

	}

}
