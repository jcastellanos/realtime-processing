import React, { useEffect, useState } from "react";

const Summary = () => {
  const [data, setData] = useState({});
  const [updated, setUpdated] = useState(false);

  useEffect(() => {
    const eventSource = new EventSource("http://localhost:8082/summary/sse");
    eventSource.onmessage = (event) => {
      const result = JSON.parse(event.data);
      setData(result);
      setUpdated(true);
      setTimeout(() => setUpdated(false), 1500);
    };
    return () => eventSource.close();
  }, []);

  return (
    <div className="container-fluid">
        <div className="row">
            <div className="col-md-3 p-1" > 
                <div class="card text-bg-info p-3">
                    <div class="card-body">
                        <p class="h2">Transactions:</p>
                        <p class="h3">{data.transactionsCount}</p>
                    </div>
                </div>
            </div>
            <div className="col-md-3 p-1">
                <div class="card text-bg-info p-3">
                    <div class="card-body">
                        <p class="h2">Successful:</p>
                        <p class="h3">{data.transactionsSuccessfulCount}</p>
                    </div>
                </div>
            </div>
            <div className="col-md-3 p-1">
                <div class="card text-bg-info p-3">
                    <div class="card-body">
                        <p class="h2">Failed:</p>
                        <p class="h3">{data.transactionsFailedCount}</p>
                    </div>
                </div>
            </div>
            <div className="col-md-3 p-1">
                <div class="card text-bg-info p-3">
                    <div class="card-body">
                        <p class="h2">Total Amount:</p>
                        <p class="h3">{data.totalAmount}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
  );
};

export default Summary;