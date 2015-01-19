%% loadar en filen
clear all
X = load('outfile.txt');

%% ritar alla partiklar
plot(X(1,2:2:end),X(1,3:2:end),'*');
axis([0 400 0 400]);
hold on

tidssteg = [0.5;diff(X(:,1))];
for i = 2:length(X(:,1))
    clf;
    plot(X(i,2:2:end),X(i,3:2:end),'*');
    drawnow;
    axis([0 400 0 400]);
    pause(tidssteg(i));
end

%% ritar partikeln i:s bana

i = 1;
plot(X(:,1+i),X(:,2+i));

%% plottar alla partiklars banor

plot(X(:,2),X(:,3));
hold on
for i=2:(length(X(1,:))-2)
    plot(X(:,1+i),X(:,2+i));  
end

%% minsta/största

mX = [];
miX = [];

mY = [];
miY = [];

medel = [];
standardav = [];

for i = 1:length(X(:,1))
   mX = [mX, max(X(i,2:2:end))];
   mY = [mY, max(X(i,3:2:end))];
   
   miX = [miX, min(X(i,2:2:end))];
   miY = [miY, min(X(i,3:2:end))];
   
   medel = [medel, mean(X(i,2:2:end))];
   standardav = [standardav, std(X(i,2:2:end))];
end

plot(X(:,1),mX);
hold on
plot(X(:,1),mY);
hold off
pause
plot(X(:,1),miX);
hold on
plot(X(:,1),miY);
pause

hold off
plot(X(:,1),medel);
hold on
plot(X(:,1),standardav);

%% avståndet mellan en partikel och dess närmsta granne

n = length(X(1,3:2:end));
m = X(:,1);

x = X(:,2:2:end);
y = X(:,3:2:end);

for(k = 1:length(m))
   
        avstand = sqrt((x(k,:)'*ones(1,n) - ones(n,1)*x(k,:)).^2 + ...
                     (y(k,:)'*ones(1,n) - ones(n,1)*y(k,:)).^2);
        
        avstand = avstand + eye(n)*max(max(avstand));
        
        D(k,:) = min(avstand);
        
end
    
    mean_min_dist = mean(D');

    plot(X(:,1), mean_min_dist)







