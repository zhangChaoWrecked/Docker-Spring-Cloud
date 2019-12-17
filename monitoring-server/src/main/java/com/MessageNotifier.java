package com;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 服务监控通知
 */
public class MessageNotifier extends AbstractStatusChangeNotifier {

    private Logger logger = LoggerFactory.getLogger(MessageNotifier.class);

    public MessageNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        String serviceName = instance.getRegistration().getName();
        String serviceUrl = instance.getRegistration().getServiceUrl();
        String status = instance.getStatusInfo().getStatus();
        Map<String, Object> details = instance.getStatusInfo().getDetails();
        StringBuilder str = new StringBuilder();
        str.append("【" + serviceName + "】");
        str.append("【服务地址】" + serviceUrl);
        str.append("【状态】" + status);
        str.append("【详情】" + JsonUtils.toJson(details));
//        System.err.println(str);
        return Mono.fromRunnable(() -> {
            //TODO 进行服务状态的通知 可以发送短信或者邮件
            logger.info(str.toString());
        });
    }

}
