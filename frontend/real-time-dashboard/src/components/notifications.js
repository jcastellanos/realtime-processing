import React, { useEffect, useState } from "react";
import { Table } from 'react-bootstrap';
import './notifications.css';

const Notifications = () => {
  const [transactions, setTransactions] = useState([]);
  const [updated, setUpdated] = useState(false);

  useEffect(() => {
    const eventSource = new EventSource("http://localhost:8081/notification/sse");
    
    eventSource.onmessage = (event) => {
      const result = JSON.parse(event.data);
      setTransactions((prevEvents) => [result, ...prevEvents]);
      setUpdated(true);
      setTimeout(() => setUpdated(false), 1500);
    };
    
    return () => eventSource.close();
  }, []);

  return (
    <div>
        <h5 className={`events-list ${updated ? 'updated' : ''}`}>Notifications (status = failure or amount > 1000)</h5>
        <Table striped bordered hover>
        <thead>
            <tr>
            <th>Time</th>
            <th>User</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Location</th>
            </tr>
        </thead>
        <tbody>
            {transactions.map(tx => (
            <tr key={tx.transaction_id}>
                <td>{tx.txtime}</td>
                <td>{tx.user}</td>
                <td>{tx.amount}</td>
                <td>{tx.status}</td>
                <td>{tx.location}</td>
            </tr>
            ))}
        </tbody>
        </Table>
    </div>
  );
};

export default Notifications;